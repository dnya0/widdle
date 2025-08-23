import axios, { AxiosError } from "axios";
import qs from "qs";

export type WordSaveRequest = {
  word: string;
  isKorean: boolean;
};

export const api = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_URL,
  withCredentials: true,
  paramsSerializer: (params) => qs.stringify(params, { arrayFormat: "repeat" }),
});

export async function hasWord(words: string[]): Promise<boolean> {
  const res = await api.get("", { params: { q: words } });
  return res.data;
}

export async function addWord(requestBody: WordSaveRequest) {
  try {
    await api.post("", { ...requestBody });
  } catch (err) {
    if (err instanceof AxiosError)
      throw new Error(err.response?.data ?? "요청 실패");
  }
}
