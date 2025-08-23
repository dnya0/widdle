import { useEffect } from "react";
import { api } from "@/utils/api";

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
      .catch(() => {});
    return () => {
      mounted = false;
    };
  }, [locale, setJamo, setWord]);
};
