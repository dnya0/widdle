import { useEffect, useRef } from "react";
import {
  initBoard,
  evaluateGuess,
  mergeKeyColor,
  Cell,
} from "@/utils/word-utils";
import { loadGuessRecord, makeKey, makeStateAndSave } from "@/utils/history";
import { showSuccess } from "@/utils/showToast";

export const useRestoreGameState = (
  lang: "kr" | "en",
  jamo: string[],
  ROWS: number,
  COLS: number,
  setBoard: React.Dispatch<React.SetStateAction<Cell[][]>>,
  setCur: React.Dispatch<React.SetStateAction<{ row: number; col: number }>>,
  setKeyColors: React.Dispatch<React.SetStateAction<Record<string, number>>>,
  setIsGameOver: React.Dispatch<React.SetStateAction<boolean>>,
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>
) => {
  const hasRestoredRef = useRef(false);

  useEffect(() => {
    if (jamo.length === 0) return;
    if (hasRestoredRef.current) return;

    const key = makeKey(lang, "gameState");
    const record = loadGuessRecord(key);

    if (!record || record.guess.length === 0) return;

    const restoredBoard = initBoard(ROWS, COLS);
    const restoredKeyColors: Record<string, number> = {};

    record.guess.forEach(([rowIdx, guess]) => {
      if (rowIdx < 0 || rowIdx >= ROWS) return;
      
      const colors = evaluateGuess(guess, jamo);

      guess.forEach((ch, colIdx) => {
        if (colIdx >= COLS) return;
        
        restoredBoard[rowIdx][colIdx] = {
          text: ch,
          colorIndex: colors[colIdx],
        };

        const prev = restoredKeyColors[ch] ?? 0;
        const newColor = mergeKeyColor(prev, colors[colIdx]);
        restoredKeyColors[ch] = newColor;
      });
    });

    setBoard(restoredBoard);
    setCur({ row: record.guess.length, col: 0 });
    setKeyColors(restoredKeyColors);

    const usedRows = record.guess.length;
    const lastGuess = record.guess[usedRows - 1]?.[1] ?? [];
    const lastColors = evaluateGuess(lastGuess, jamo);
    const won = lastColors.every((c) => c === 3);
    const lost = !won && usedRows >= ROWS;
    if (won || lost) {
      setIsGameOver(true);
      setShowModal(true);
      showSuccess(lang === "kr" ? `축하드립니다! 🎉🎉🎉` : `Congrats! 🎉🎉🎉`);
      makeStateAndSave(lang, lastColors, { row: usedRows - 1, col: 0 }, ROWS);
    }

    hasRestoredRef.current = true;
  }, [
    lang,
    jamo,
    ROWS,
    COLS,
    setBoard,
    setCur,
    setKeyColors,
    setIsGameOver,
    setShowModal,
  ]);
};
