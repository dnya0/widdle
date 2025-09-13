import Square from "@/components/alphabet";

type Cell = { text: string; colorIndex?: number };
export default function EnglishTextBox({ squares }: { squares: Cell[][] }) {
  return (
    <div style={{ display: "flex", flexDirection: "column"}}>
      {squares.map((row, r) => (
        <div
          key={r}
          style={{ display: "flex", justifyContent: "center" }}
        >
          {row.map((cell, c) => (
            <Square key={c} text={cell.text} size={3} colorIndex={cell.colorIndex} />
          ))}
        </div>
      ))}
    </div>
  );
}
