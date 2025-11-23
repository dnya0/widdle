"use client";

import { loadStatsRacord, makeKey } from "@/utils/history";
import { useEffect } from "react";
import WinDistributionChart from "./win-distribution-chart";
import { MidnightTimer } from "./midnight-timer";
import EmojiExporter from "./share";
import { X } from "react-feather";

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

  const isAllZero = distribution.every((v) => v === 0);

  return (
    <div className="fixed inset-0 z-[9999] flex items-center justify-center bg-gray-800/50 z-50">
      <div className="bg-white p-6 rounded-lg shadow-lg w-[400px] text-center relative">
        <div className="relative flex items-center justify-center mb-4">
          <h2 className="text-lg font-bold absolute left-1/2 -translate-x-1/2" style={{ color: "black"}}>
            {lang === "kr" ? "통계" : "Statistics"}
          </h2>
          <X
            className="ml-auto text-gray-500 hover:text-gray-800 cursor-pointer"
            onClick={onClose}
          />
        </div>
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
            <div style={{ fontFamily: "Pretendard-Bold", fontSize: "1.6rem", color: "black"}}>
              {stats ? stats.totalStreak : 0}
            </div>
            <div style={{ fontSize: "0.8rem", color: "black" }}>
              {lang === "kr" ? "전체도전" : "TotalStreak"}
            </div>
          </div>
          <div>
            <div style={{ fontFamily: "Pretendard-Bold", fontSize: "1.6rem", color: "black"}}>
              {stats ? stats.successRate : 0}%
            </div>
            <div style={{ fontSize: "0.8rem", color: "black" }}>
              {lang === "kr" ? "정답률" : "SuccessRate"}
            </div>
          </div>
          <div>
            <div style={{ fontFamily: "Pretendard-Bold", fontSize: "1.6rem", color: "black" }}>
              {stats ? stats.currentStreak : 0}
            </div>
            <div style={{ fontSize: "0.8rem", color: "black" }}>
              {lang === "kr" ? "최근 연속 정답" : "CurrentStreak"}
            </div>
          </div>
          <div>
            <div style={{ fontFamily: "Pretendard-Bold", fontSize: "1.6rem", color: "black" }}>
              {stats ? stats.bestStreak : 0}
            </div>
            <div style={{ fontSize: "0.8rem", color: "black" }}>
              {lang === "kr" ? "최다 연속 정답" : "BestStreak"}
            </div>
          </div>
        </div>

        {/* {!isAllZero && ( */}
          <>
            <h2 className="text-lg font-bold mb-4" style={{ marginTop: 20, color: "black" }}>
              {lang === "kr" ? "정답 분포" : "Distribution"}
            </h2>
            <WinDistributionChart winDistribution={distribution} />
          </>
        {/* )} */}
        {isGameOver && (
          <>
            <p
              className="mb-4"
              style={{ fontFamily: "Pretendard-Medium", fontSize: "1.1rem", color: "black" }}
            >
              {lang === "kr"
                ? `정답은 '${answer}' 입니다.`
                : `The answer was '${answer}'. `}
            </p>
          </>
        )}
        {isGameOver && (
          <>
            <div
              className="mb-4"
              style={{ display: "grid", gridTemplateColumns: "repeat(2, 1fr)" }}
            >
              <div>
                <div style={{ fontFamily: "Pretendard-Medium", color: "black" }}>
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
