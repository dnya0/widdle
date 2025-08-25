"use client";

import { useState } from "react";

import { Cell, initBoard } from "@/utils/word-utils";
import { useBackspace } from "@/hooks/use-backspace";
import { useKeyPress } from "@/hooks/use-key-press";
import { useHandleEnter } from "@/hooks/use-handle-enter";
import { useFetchGameData } from "@/hooks/use-fetch-game-data";
import { useRestoreGameState } from "@/hooks/use-restore-game-state";
import EnglishKeyboard from "./en-keyboard";
import EnglishTextBox from "./en-text-box";

const ROWS = 5,
  COLS = 5;

const LANG = "en";

export default function EnClient() {
  const [board, setBoard] = useState<Cell[][]>(initBoard(5, 5));
  const [keyColors, setKeyColors] = useState<Record<string, number>>({});
  const [cur, setCur] = useState({ row: 0, col: 0 });
  const [isGameOver, setIsGameOver] = useState(false);

  const [jamo, setJamo] = useState<string[]>([]);
  const [word, setWord] = useState<string>("");

  useFetchGameData(LANG, setJamo, setWord);
  useRestoreGameState(LANG, jamo, setBoard, setCur, setKeyColors);

  const handleEnter = useHandleEnter(
    isGameOver,
    board,
    cur,
    setBoard,
    setCur,
    setKeyColors,
    setIsGameOver,
    jamo,
    ROWS,
    word,
    LANG
  );

  const onKeyPress = useKeyPress(isGameOver, cur, setBoard, setCur, COLS);
  const onBackspace = useBackspace(isGameOver, cur, setBoard, setCur);

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        gap: 8,
        alignItems: "center",
      }}
    >
      <EnglishTextBox squares={board} />
      <div
        style={{
          margin: 10,
        }}
      ></div>
      <EnglishKeyboard
        onKeyPress={onKeyPress}
        onBackspace={onBackspace}
        onEnter={handleEnter}
        keyColors={keyColors}
      />
    </div>
  );
}
