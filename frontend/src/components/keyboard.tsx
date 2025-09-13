"use client";

import { colors } from "@/utils/word-utils";

type KeyboardProps = {
  widthSize?: number;
  heightSize?: number;
  colorIndex?: number;
  text?: string;
  onClick?: () => void;
};

export default function Keyboard({
  heightSize = 4,
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

        width: `${widthSize}vw`,
        height: `${heightSize}vh`,
        minWidth: "30px",
        minHeight: "60px",
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
