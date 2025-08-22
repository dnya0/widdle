import { Cell } from "@/utils/word-utils";

export const useBackspace = (
  isGameOver: boolean,
  cur: { row: number; col: number },
  setBoard: React.Dispatch<React.SetStateAction<Cell[][]>>,
  setCur: React.Dispatch<React.SetStateAction<{ row: number; col: number }>>
) => {
  const onBackspace = () => {
    if (isGameOver) return;

    setBoard((prev) => {
      const row = cur.row;
      const lastFilled = prev[row].findLastIndex((c) => c.text);

      if (lastFilled < 0) return prev;
      const copy = prev.map((r) => r.map((c) => ({ ...c })));
      copy[row][lastFilled].text = "";
      setCur({ row, col: lastFilled });

      copy[row] = copy[row].map((c) => ({
        ...c,
        colorIndex: undefined,
      }));
      return copy;
    });
  };

  return onBackspace;
};