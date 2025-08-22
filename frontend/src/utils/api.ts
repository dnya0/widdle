import axios from "axios";
import qs from "qs";

export const api = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_URL,
  withCredentials: true,
  paramsSerializer: (params) => qs.stringify(params, { arrayFormat: "repeat" }),
});

export async function hasWord(words: string[]): Promise<boolean> {
  const res = await api.get("", { params: { q: words } });
  return res.data;
}
