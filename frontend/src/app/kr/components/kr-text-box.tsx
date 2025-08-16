import Square from "@/components/alphabet";

type Cell = { text: string; colorIndex?: number };
export default function KoreanTextBox({ squares }: { squares: Cell[][] }) {
  return (
    <div style={{ display: "flex", flexDirection: "column"}}>
      {squares.map((row, r) => (
        <div
          key={r}
          style={{ display: "flex", justifyContent: "center" }}
        >
          {row.map((cell, c) => (
            <Square key={c} text={cell.text} colorIndex={cell.colorIndex} />
          ))}
        </div>
      ))}
    </div>
  );
}
