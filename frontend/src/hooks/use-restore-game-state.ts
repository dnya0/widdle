import { useEffect } from "react";
import {
  initBoard,
  evaluateGuess,
  mergeKeyColor,
  Cell,
} from "@/utils/word-utils";
import { loadGuessRecord, makeKey } from "@/utils/history";

export const useRestoreGameState = (
  lang: "kr" | "en",
  jamo: string[],
  setBoard: React.Dispatch<React.SetStateAction<Cell[][]>>,
  setCur: React.Dispatch<React.SetStateAction<{ row: number; col: number }>>,
  setKeyColors: React.Dispatch<React.SetStateAction<Record<string, number>>>
) => {
  useEffect(() => {
    const key = makeKey(lang, "gameState");
    const record = loadGuessRecord(key);

    if (record && record.guess.length > 0) {
      const restoredBoard = initBoard();
      const restoredKeyColors: Record<string, number> = {};

      record.guess.forEach(([rowIdx, guess]) => {
        const colors = evaluateGuess(guess, jamo);

        guess.forEach((ch, colIdx) => {
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
    }
  }, [jamo, lang, setBoard, setCur, setKeyColors]);
};
