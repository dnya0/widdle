"use client";

import { loadStatsRacord, makeKey } from "@/utils/history";
import { useEffect } from "react";
import WinDistributionChart from "./win-distribution-chart";
import { MidnightTimer } from "./midnight-timer";
import EmojiExporter from "./share";

interface ResultModalProps {
  showModal: boolean;
  isGameOver: boolean;
  answer: string;
  lang: "kr" | "en";
  onClose: () => void;
}

export default function ResultModal({
  showModal,
  isGameOver,
  answer,
  lang,
  onClose,
}: ResultModalProps) {
  useEffect(() => {
    if (showModal) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "auto";
    }
  }, [showModal]);

  if (!showModal) return null;

  const key = makeKey(lang, "gameStats");
  const stats = loadStatsRacord(key);

  const distribution = stats
    ? stats.winDistribution
    : lang === "kr"
    ? [0, 0, 0, 0, 0, 0]
    : [0, 0, 0, 0, 0];

  return (
    <div className="fixed inset-0 z-[9999] flex items-center justify-center bg-gray-800/50 z-50">
      <div className="bg-white p-6 rounded-lg shadow-lg w-[400px] text-center relative">
        <button
          onClick={onClose}
          className="absolute top-5 right-6 text-gray-500 hover:text-gray-800"
        >
          ✕
        </button>
        {isGameOver && (
          <>
            <h2 className="text-lg font-bold mb-4">
              {lang === "kr" ? "게임 종료 🎉" : "Game Over 🎉"}
            </h2>
            <p className="mb-4" style={{ fontFamily: "Pretendard-Medium" }}>
              {lang === "kr"
                ? `정답은 '${answer}' 입니다.`
                : `The answer was '${answer}'.`}
            </p>
          </>
        )}
        <h2 className="text-lg font-bold mb-4">
          {lang === "kr" ? "통계" : "Statistics"}
        </h2>
        <div
          style={{
            display: "grid",
            gridTemplateColumns: "repeat(4, 1fr)",
            gap: "1rem",
            fontFamily: "Pretendard-Medium",
            textAlign: "center",
          }}
        >
          <div>
            <div style={{ fontFamily: "Pretendard-Bold", fontSize: "1.6rem" }}>
              {stats ? stats.totalStreak : 0}
            </div>
            <div style={{ fontSize: "0.8rem" }}>
              {lang === "kr" ? "전체도전" : "TotalStreak"}
            </div>
          </div>
          <div>
            <div style={{ fontFamily: "Pretendard-Bold", fontSize: "1.6rem" }}>
              {stats ? stats.successRate : 0}%
            </div>
            <div style={{ fontSize: "0.8rem" }}>
              {lang === "kr" ? "정답률" : "SuccessRate"}
            </div>
          </div>
          <div>
            <div style={{ fontFamily: "Pretendard-Bold", fontSize: "1.6rem" }}>
              {stats ? stats.currentStreak : 0}
            </div>
            <div style={{ fontSize: "0.8rem" }}>
              {lang === "kr" ? "최근 연속 정답" : "CurrentStreak"}
            </div>
          </div>
          <div>
            <div style={{ fontFamily: "Pretendard-Bold", fontSize: "1.6rem" }}>
              {stats ? stats.bestStreak : 0}
            </div>
            <div style={{ fontSize: "0.8rem" }}>
              {lang === "kr" ? "최다 연속 정답" : "BestStreak"}
            </div>
          </div>
        </div>

        <h2 className="text-lg font-bold mb-4" style={{ marginTop: 20 }}>
          {lang === "kr" ? "정답 분포" : "Distribution"}
        </h2>
        <WinDistributionChart winDistribution={distribution} />
        {isGameOver && (
          <>
            <div
              className="mb-4"
              style={{ display: "grid", gridTemplateColumns: "repeat(2, 1fr)" }}
            >
              <div>
                <div style={{ fontFamily: "Pretendard-Medium" }}>
                  {lang === "kr"
                    ? "다음 게임까지"
                    : `The answer was '${answer}'.`}
                </div>
                <MidnightTimer></MidnightTimer>
              </div>

              <EmojiExporter lang={lang}></EmojiExporter>
            </div>
          </>
        )}
      </div>
    </div>
  );
}
