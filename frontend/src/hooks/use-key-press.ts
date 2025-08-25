import { hasWord } from "@/utils/api";
import { Cell } from "@/utils/word-utils";
import { useCallback } from "react";

export const useKeyPress = (
  isGameOver: boolean,
  cur: { row: number; col: number },
  setBoard: React.Dispatch<React.SetStateAction<Cell[][]>>,
  setCur: React.Dispatch<React.SetStateAction<{ row: number; col: number }>>,
  COLS: number
) => {
  const onKeyPress = useCallback(
    (ch: string) => {
      if (isGameOver || cur.col >= COLS) return;

      setBoard((prev) => {
        const copy = prev.map((r) => r.map((c) => ({ ...c })));
        copy[cur.row][cur.col].text = ch;
        setCur({ row: cur.row, col: Math.min(cur.col + 1, COLS) });

        const word = copy[cur.row].map((c) => c.text);

        if (cur.col === COLS - 1) {
          hasWord(word).then((exists) => {
            if (!exists) {
              setBoard((prev) => {
                const copy = prev.map((r) => r.map((c) => ({ ...c })));
                copy[cur.row] = copy[cur.row].map((c) => ({
                  ...c,
                  colorIndex: -1,
                }));
                return copy;
              });
            }
          });
        }
        return copy;
      });
    },
    [isGameOver, cur, setBoard, setCur, COLS]
  );

  return onKeyPress;
};
