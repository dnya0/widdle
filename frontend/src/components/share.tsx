"use client";

import { getAnswer } from "@/utils/api";
import { loadGuessRecord, loadStatsRacord, makeKey } from "@/utils/history";
import { showSuccess } from "@/utils/showToast";
import { evaluateGuess } from "@/utils/word-utils";
import toast from "react-hot-toast";

function getTodayIndex(startDateStr = "2025-08-29"): number {
  const startDate = new Date(startDateStr);
  const today = new Date();

  startDate.setHours(0, 0, 0, 0);
  today.setHours(0, 0, 0, 0);

  const diffInMs = today.getTime() - startDate.getTime();
  const diffInDays = Math.floor(diffInMs / (1000 * 60 * 60 * 24));

  return diffInDays + 1;
}

export default function EmojiExporter({ lang }: { lang: "kr" | "en" }) {

  const toEmoji = (colors: number[]) =>
    colors
      .map((c) => {
        if (c === 3) return "🟩";
        if (c === 2) return "🟨";
        return "⬜️";
      })
      .join("");

  const handleTransformAndCopy = async () => {
    const key = makeKey(lang, "gameState");
    const record = loadGuessRecord(key);
    const stats = loadStatsRacord(makeKey(lang, "gameStats"));
    const guesses = record?.guess;

    const answer = await getAnswer(lang);
    if (!answer) {
      toast.error(
        lang === "kr"
          ? "데이터 형식이 올바르지 않습니다."
          : "Unexpected API response"
      );
      return;
    }

    const result = guesses?.map(([, guess]) => {
      const colors = evaluateGuess(guess, answer.jamo);
      return toEmoji(colors);
    });

    if (!result || !guesses) return;

    const todayIndex = getTodayIndex();
    const isSuccess = guesses?.some(([, guess]) =>
      evaluateGuess(guess, answer.jamo).every((c) => c === 3)
    );
    const successRow =
      guesses.findIndex(([, guess]) =>
        evaluateGuess(guess, answer.jamo).every((c) => c === 3)
      ) + 1;

    const header = isSuccess
      ? `widdle.day ${todayIndex} ${successRow}/6 🔥${stats?.currentStreak}`
      : `widdle.day ${todayIndex} X/6 widdle.day 💧`;

    try {
      const exportText = [header, "", ...result].join("\n");
      await navigator.clipboard.writeText(exportText);

      showSuccess(
        lang === "kr"
          ? "결과가 클립보드에 복사되었습니다."
          : "The result has been copied to the clipboard."
      );
    } catch (err) {
      console.error("복사 실패:", err);
    }
  };

  return (
    <button
      type="button"
      className="text-white bg-indigo-600 hover:bg-indigo-800 focus:ring-4 focus:ring-indigo-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
      onClick={handleTransformAndCopy}
    >
      {lang === "kr" ? "공유하기" : "Share"}
    </button>
  );
}
