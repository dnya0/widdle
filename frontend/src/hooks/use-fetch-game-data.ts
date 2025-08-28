import { useEffect } from "react";
import { api } from "@/utils/api";
import toast from "react-hot-toast";
import { AxiosError } from "axios";

type GameData = {
  id: string;
  jamo: string[];
  word: string;
  isKorean: boolean;
  length: number;
};

export const useFetchGameData = (
  locale: "kr" | "en",
  setJamo: React.Dispatch<React.SetStateAction<string[]>>,
  setWord: React.Dispatch<React.SetStateAction<string>>
) => {
  useEffect(() => {
    let canceled = false;
    const controller = new AbortController();

    (async () => {
      try {
        const res = await api.get<GameData>(`/${locale}`, {
          signal: controller.signal,
        });
        const data = res.data;

        if (
          !data ||
          !Array.isArray(data.jamo) ||
          typeof data.word !== "string"
        ) {
          console.error("Unexpected API response:", data);
          toast.error("데이터 형식이 올바르지 않습니다.");
          return;
        }

        if (!canceled) {
          setJamo(data.jamo);
          setWord(data.word);
        }
      } catch (err) {
        const ax = err as AxiosError;
        if (ax.code === "ERR_CANCELED") return;
        const msg = ax.message ?? "요청 실패";
        toast.error(String(msg));
        console.error("API error:", ax.response?.data ?? ax);
      }
    })();

    return () => {
      canceled = true;
      controller.abort();
    };
  }, [locale, setJamo, setWord]);
};
