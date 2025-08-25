import { useEffect } from "react";
import { api } from "@/utils/api";
import toast from "react-hot-toast";
import { AxiosError } from "axios";

export const useFetchGameData = (
  locale: "kr" | "en",
  setJamo: React.Dispatch<React.SetStateAction<string[]>>,
  setWord: React.Dispatch<React.SetStateAction<string>>
) => {
  useEffect(() => {
    let mounted = true;
    api
      .get(`/${locale}`)
      .then((res) => {
        if (!mounted) return;
        setJamo(res.data.jamo);
        setWord(res.data.word);
      })
      .catch((err) => {
        if (err instanceof AxiosError) toast(err.response?.data ?? "요청 실패");
      });
    return () => {
      mounted = false;
    };
  }, [locale, setJamo, setWord]);
};
