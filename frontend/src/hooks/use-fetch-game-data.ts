import { useEffect } from "react";
import { api } from "@/utils/api";

export const useFetchGameData = (setJamo: React.Dispatch<React.SetStateAction<string[]>>, setWord: React.Dispatch<React.SetStateAction<string>>) => {
  useEffect(() => {
    api.get("/kr").then((res) => {
      setJamo(res.data.jamo);
      setWord(res.data.word);
    });
  }, [setJamo, setWord]);
};