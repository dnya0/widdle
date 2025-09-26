import axios, { AxiosError } from "axios";
import qs from "qs";

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

export async function hasWord(words: string[]): Promise<boolean> {
  try {
    const res = await api.get("", { params: { q: words } });
    return res.data;
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
    const res = await api.get<GameData>(`/${locale}`);
    const data = res.data;

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
