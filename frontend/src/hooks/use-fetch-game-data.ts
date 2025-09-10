import { useEffect } from "react";
import { getAnswer } from "@/utils/api";
import toast from "react-hot-toast";
import { AxiosError } from "axios";

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
        const data = await getAnswer(locale);

        if (!data) {
          toast.error(
            locale === "kr"
              ? "데이터 형식이 올바르지 않습니다."
              : "Unexpected API response"
          );
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
