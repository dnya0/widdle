"use client";

import { useEffect, useState } from "react";
import KoreanTextBox from "./kr-text-box";
import KoreanKeyboard from "./kr-keyboard";
import {
  Cell,
  evaluateGuess,
  initBoard,
  mergeKeyColor,
} from "@/utils/word-utils";
import {
  loadGuessRecord,
  makeKey,
  saveGuess,
  saveStats,
} from "@/utils/history";

const ROWS = 6,
  COLS = 6;

export default function KrClient() {
  const [board, setBoard] = useState<Cell[][]>(initBoard());
  const [keyColors, setKeyColors] = useState<Record<string, number>>({});
  const [cur, setCur] = useState({ row: 0, col: 0 });
  const [isGameOver, setIsGameOver] = useState(false);

  const answer = ["ㄱ", "ㅏ", "ㄷ", "ㅏ", "ㄴ", "ㅣ"];

  useEffect(() => {
    const key = makeKey("ko", "gameState");
    const record = loadGuessRecord(key);

    if (record && record.guess.length > 0) {
      const restoredBoard = initBoard();
      const restoredKeyColors: Record<string, number> = {};

      record.guess.forEach(([rowIdx, guess]) => {
        // 각 행 정답 채점
        const colors = evaluateGuess(guess, answer);

        // 보드 채우기
        guess.forEach((ch, colIdx) => {
          restoredBoard[rowIdx][colIdx] = {
            text: ch,
            colorIndex: colors[colIdx],
          };

          // 키보드 색상 갱신
          const prev = restoredKeyColors[ch] ?? 0;
          const newColor = mergeKeyColor(prev, colors[colIdx]);
          restoredKeyColors[ch] = newColor;
        });
      });

      setBoard(restoredBoard);
      setCur({ row: record.guess.length, col: 0 });
      setKeyColors(restoredKeyColors); // 키보드 색상도 반영
    }
  }, []);

  /**
   * enter 눌렀을 때
   * @returns
   */
  const handleEnter = () => {
    if (isGameOver) return;

    console.log("[KrClient] handleEnter");
    const guess = board[cur.row].map((c) => c.text);
    if (guess.includes("") || guess.includes(" ")) {
      return;
    }
    console.log(guess);
    const colors = evaluateGuess(guess, answer);

    setBoard((prev) => {
      const copy = prev.map((r) => r.map((c) => ({ ...c })));
      colors.forEach((color, i) => (copy[cur.row][i].colorIndex = color));
      return copy;
    });

    setKeyColors((prev) => {
      const next = { ...prev };
      guess.forEach((ch, i) => {
        const newColor = mergeKeyColor(prev[ch], colors[i]);
        if (!next[ch] || next[ch] < newColor) {
          next[ch] = newColor;
        }
      });
      return next;
    });

    if (colors.every((c) => c === 3) || cur.row === ROWS - 1) {
      setIsGameOver(true);

      const winDistribution = [0, 0, 0, 0, 0, 0];
      if (cur.row === ROWS - 1 && !colors.every((c) => c === 3)) {
        saveStats({
          bestStreak: 0,
          currentStreak: 0,
          totalStreak: 1,
          successRate: 0,
          lang: "ko",
          winDistribution: winDistribution,
        });
        return;
      }

      winDistribution[cur.row] = 1;

      saveStats({
        bestStreak: 1,
        currentStreak: 1,
        totalStreak: 1,
        successRate: 100,
        lang: "ko",
        winDistribution: winDistribution,
      });

      return;
    }

    saveGuess("ko", guess);

    setCur({ row: Math.min(cur.row + 1, ROWS - 1), col: 0 });
  };

  const onKeyPress = (ch: string) => {
    if (isGameOver || cur.col >= COLS) return;

    setBoard((prev) => {
      const copy = prev.map((r) => r.map((c) => ({ ...c })));
      copy[cur.row][cur.col].text = ch;
      setCur({ row: cur.row, col: Math.min(cur.col + 1, COLS) });
      return copy;
    });
  };

  const onBackspace = () => {
    if (isGameOver) return;

    setBoard((prev) => {
      const row = cur.row;
      const lastFilled = prev[row].findLastIndex((c) => c.text);

      if (lastFilled < 0) return prev;
      const copy = prev.map((r) => r.map((c) => ({ ...c })));
      copy[row][lastFilled].text = "";
      setCur({ row, col: lastFilled });
      return copy;
    });
  };

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        gap: 8,
        alignItems: "center",
      }}
    >
      <KoreanTextBox squares={board} />
      <KoreanKeyboard
        onKeyPress={onKeyPress}
        onBackspace={onBackspace}
        onEnter={handleEnter}
        keyColors={keyColors}
      />
    </div>
  );
}
