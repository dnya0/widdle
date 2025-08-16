"use client";

import { useState } from "react";
import KoreanTextBox from "./kr-text-box";
import KoreanKeyboard from "./kr-keyboard";
import { evaluateGuess } from "@/utils/word-utils";

type Cell = { text: string; colorIndex?: number };
const ROWS = 6,
  COLS = 6;

export default function KrClient() {
  const [board, setBoard] = useState<Cell[][]>(
    Array.from({ length: ROWS }, () =>
      Array.from({ length: COLS }, () => ({ text: "" }))
    )
  );
  const [cur, setCur] = useState({ row: 0, col: 0 });

  const handleEnter = () => {
    console.log("[KrClient] handleEnter");
    const guess = board[cur.row].map((c) => c.text);
    if (guess.includes("") || guess.includes(" ")) {
      return;
    }
    console.log(guess);
    const answer = ["ㄱ", "ㅏ", "ㄷ", "ㅏ", "ㄴ", "ㅣ"];
    const colors = evaluateGuess(guess, answer);

    setBoard((prev) => {
      const copy = prev.map((r) => r.map((c) => ({ ...c })));
      colors.forEach((color, i) => {
        copy[cur.row][i].colorIndex = color;
      });
      return copy;
    });
    setCur({ row: Math.min(cur.row + 1, ROWS - 1), col: 0 });
  };

  const onKeyPress = (ch: string) => {
    if (cur.col >= COLS) return;

    setBoard((prev) => {
      const copy = prev.map((r) => r.map((c) => ({ ...c })));
      copy[cur.row][cur.col].text = ch;
      setCur({ row: cur.row, col: Math.min(cur.col + 1, COLS) });
      return copy;
    });
  };

  const onBackspace = () => {
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
      />
    </div>
  );
}
