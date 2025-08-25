"use client";

import { useEffect } from "react";

interface ResultModalProps {
  isGameOver: boolean;
  answer: string;
  lang: "kr" | "en";
  onClose: () => void;
}

export default function ResultModal({
  isGameOver,
  answer,
  lang,
  onClose,
}: ResultModalProps) {
  useEffect(() => {
    if (isGameOver) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "auto";
    }
  }, [isGameOver]);

  if (!isGameOver) return null;

  return (
    <div className="fixed inset-0 z-[9999] flex items-center justify-center bg-black/50 z-50">
      <div className="bg-white p-6 rounded-lg shadow-lg w-[320px] text-center relative">
        <button
          onClick={onClose}
          className="absolute top-3 right-3 text-gray-500 hover:text-gray-800"
        >
          ✕
        </button>
        <h2 className="text-lg font-bold mb-4">
          {lang === "kr" ? "게임 종료 🎉" : "Game Over 🎉"}
        </h2>
        <p className="mb-4">
          {lang === "kr"
            ? `정답은 '${answer}' 입니다.`
            : `The answer was '${answer}'.`}
        </p>
        <button
          onClick={onClose}
          className="mt-4 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
        >
          {lang === "kr" ? "닫기" : "Close"}
        </button>
      </div>
    </div>
  );
}
