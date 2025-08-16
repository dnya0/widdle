export function insertChar(
  prev: { text: string; colorIndex?: number }[][],
  row: number,
  col: number,
  char: string
) {
  const rows = prev.length;
  const cols = prev[0].length;

  const copy = prev.map(r => r.map(c => ({ ...c })));
  copy[row][col].text = char;

  let nextRow = row;
  let nextCol = col + 1;
  if (nextCol >= cols) {
    nextCol = 0;
    nextRow = Math.min(row + 1, rows - 1);
  }

  return { squares: copy, nextRow, nextCol };
}