"use client";

import { useMemo, useState } from "react";
import { Cell, initBoard } from "@/utils/word-utils";
import { useFetchGameData } from "@/hooks/use-fetch-game-data";
import { useRestoreGameState } from "@/hooks/use-restore-game-state";
import { useHandleEnter } from "@/hooks/use-handle-enter";
import { useKeyPress } from "@/hooks/use-key-press";
import { useBackspace } from "@/hooks/use-backspace";
import ResultModal from "@/components/result-modal";
import dynamic from "next/dynamic";

type Lang = "kr" | "en";

type GameClientProps = {
  lang: Lang;
  rows: number;
  cols: number;
};

export default function GameClient({
  lang,
  rows,
  cols,
}: GameClientProps) {
  const [board, setBoard] = useState<Cell[][]>(initBoard(rows, cols));
  const [keyColors, setKeyColors] = useState<Record<string, number>>({});
  const [cur, setCur] = useState({ row: 0, col: 0 });
  const [isGameOver, setIsGameOver] = useState(false);

  const [jamo, setJamo] = useState<string[]>([]);
  const [word, setWord] = useState<string>("");
  const [showModal, setShowModal] = useState(false);

  const TextBox = useMemo(() => (
  lang === "kr"
    ? dynamic(() => import("@/app/kr/components/kr-text-box"), { ssr: false })
    : dynamic(() => import("@/app/en/components/en-text-box"), { ssr: false })
), [lang]);

const Keyboard = useMemo(() => (
  lang === "kr"
    ? dynamic(() => import("@/app/kr/components/kr-keyboard"), { ssr: false })
    : dynamic(() => import("@/app/en/components/en-keyboard"), { ssr: false })
), [lang]);


  useFetchGameData(lang, setJamo, setWord);
  useRestoreGameState(
    lang,
    jamo,
    rows,
    setBoard,
    setCur,
    setKeyColors,
    setIsGameOver,
    setShowModal
  );

  const handleEnter = useHandleEnter(
    isGameOver,
    board,
    cur,
    setBoard,
    setCur,
    setKeyColors,
    setIsGameOver,
    jamo,
    rows,
    lang,
    setShowModal
  );

  const onKeyPress = useKeyPress(isGameOver, cur, setBoard, setCur, cols);
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
      <ResultModal
        showModal={showModal}
        isGameOver={isGameOver}
        answer={word}
        lang={lang}
        onClose={() => setShowModal(false)}
      />
      <TextBox squares={board} />
      <div style={{ margin: 10 }} />
      <Keyboard
        onKeyPress={onKeyPress}
        onBackspace={onBackspace}
        onEnter={handleEnter}
        keyColors={keyColors}
      />
    </div>
  );
}
