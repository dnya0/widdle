import axios, { AxiosError } from "axios";
import { assemble } from "es-hangul";
import qs from "qs";
import {
  assembleAndCombineConsonants,
  assembleAndCombineVowels,
  checkCAndVBalance,
  checkRepetitiveJamo,
} from "./word-utils";

interface ApiResponse<T> {
  code: string;
  status: number;
  message: string | null;
  data: T;
}

export type WordSaveRequest = {
  word: string;
  isKorean: boolean;
};

export type GameData = {
  id: string;
  jamo: string[];
  word: string;
  isKorean: boolean;
  length: number;
};

export const api = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_URL,
  withCredentials: true,
  paramsSerializer: (params) => qs.stringify(params, { arrayFormat: "repeat" }),
});

export async function hasWord(
  words: string[],
  locale: "kr" | "en"
): Promise<boolean> {
  try {

    let word: string;
    if (locale === "kr") {
      if (checkRepetitiveJamo(words)) {
        return false;
      }
      if (!checkCAndVBalance(words)) {
        console.warn("Input must contain both consonants and vowels.");
        return false;
      }
      const combineArray = assembleAndCombineConsonants(words);
      const combinedWordsArray = assembleAndCombineVowels(combineArray);
      word = assemble(combinedWordsArray);
    } else {
      word = words.join("");
    }
    const res = await api.get("", { params: { word: word, q: words } });
    const response = res.data;

    if (response.status === 200) {
      return response.data;
    } else {
      return false;
    }
  } catch (error) {
    console.error("Error checking if word exists:", error);
    return false; // 또는 적절한 default 값을 반환
  }
}

export async function addWord(requestBody: WordSaveRequest) {
  try {
    await api.post("", { ...requestBody });
  } catch (err) {
    if (err instanceof AxiosError)
      throw new Error(err.response?.data ?? "요청 실패");
  }
}

export async function getAnswer(locale: "kr" | "en"): Promise<GameData | null> {
  try {
    const res = await api.get<ApiResponse<GameData>>(`/${locale}`);

    const responseBody = res.data;
    const data = responseBody.data;

    if (!data || !Array.isArray(data.jamo) || typeof data.word !== "string") {
      return null;
    }
    return data;
  } catch (err) {
    const ax = err as AxiosError;
    // console.error("API error:", ax.response?.data ?? ax.message ?? err);
    return null;
  }
}
