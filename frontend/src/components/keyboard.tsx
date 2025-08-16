"use client";

type KeyboardProps = {
  widthSize?: number;
  heightSize?: number;
  colorIndex?: number;
  text?: string;
  onClick?: () => void;
};

const colors = [
  "#dfdfdfff",
  "rgba(238, 190, 0, 1)",
  "rgba(17, 198, 0, 1)",
  "#a4a4a4ff",
];

export default function Keyboard({
  heightSize = 58,
  widthSize = heightSize * 0.64,
  colorIndex = 0,
  text,
  onClick,
}: KeyboardProps) {
  const color = colors[colorIndex];
  const textColor = colorIndex === 0 ? "#000" : "#fff";
  return (
    <button
      style={{
        width: widthSize,
        height: heightSize,
        backgroundColor: color,
        border: `2px solid ${color}`,
        borderRadius: 5,
        display: "inline-flex",
        verticalAlign: "middle",
        boxSizing: "border-box",
        justifyContent: "center",
        alignItems: "center",
        fontWeight: "bold",
        fontSize: "1rem",
        color: textColor,
        margin: "2px",
        textTransform: "uppercase",
      }}
      onClick={onClick}
    >
      {text}
    </button>
  );
}
