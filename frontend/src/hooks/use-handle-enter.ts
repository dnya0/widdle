import { Cell, evaluateGuess, mergeKeyColor } from "@/utils/word-utils";
import { makeStateAndSave, saveGuess } from "@/utils/history";
import { hasWord } from "@/utils/api";
import toast from "react-hot-toast";

export const useHandleEnter = (
  isGameOver: boolean,
  board: Cell[][],
  cur: { row: number; col: number },
  setBoard: React.Dispatch<React.SetStateAction<Cell[][]>>,
  setCur: React.Dispatch<React.SetStateAction<{ row: number; col: number }>>,
  setKeyColors: React.Dispatch<React.SetStateAction<Record<string, number>>>,
  setIsGameOver: React.Dispatch<React.SetStateAction<boolean>>,
  jamo: string[],
  ROWS: number
) => {
  const handleEnter = async () => {
    if (isGameOver) return;

    const guess = board[cur.row].map((c) => c.text);
    if (guess.includes("") || guess.includes(" ")) {
      return;
    }

    const exists = await hasWord(guess);
    if (!exists) {
      toast.error("단어가 존재하지 않습니다.");
      return;
    }

    const colors = evaluateGuess(guess, jamo);

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
      //틀렷을 경우 정답을 알려주는 토스트메세지 추가
      makeStateAndSave("kr", colors, cur, ROWS);
    }

    saveGuess("kr", guess);

    setCur({ row: Math.min(cur.row + 1, ROWS - 1), col: 0 });
  };

  return handleEnter;
};
