import { colorRed, colors } from "@/utils/word-utils";

type SquareProps = {
  size?: number; // 네모 크기
  colorIndex?: number;
  text?: string;
};

function getBorderColor({
  colorIndex,
  text,
}: {
  colorIndex?: number;
  text?: string;
}) {
  if (colorIndex !== undefined) return colors[colorIndex]; // 색이 있을 때
  return text ? "#000" : "#ccc";
}

export default function Square({ size = 4, colorIndex, text }: SquareProps) {
  const backgroundColor =
    colorIndex !== undefined && colors[colorIndex]
      ? colors[colorIndex]
      : "#fff";
  const borderColor = getBorderColor({ colorIndex, text });

  const textColor =
    colorIndex !== undefined && colorIndex >= 0
      ? "#fff"
      : colorIndex === -1
      ? colorRed
      : "#000";

  const textSet = text === undefined || text === null ? " " : text;

  return (
    <div
      style={{
        width: `min(${size}vw, ${size}vh)`,
        height: `min(${size}vw, ${size}vh)`,
        minWidth: "40px",
        minHeight: "40px",
        backgroundColor: backgroundColor,
        border: `2px solid ${borderColor}`,
        borderRadius: 5,
        display: "inline-flex",
        verticalAlign: "middle",
        boxSizing: "border-box",
        justifyContent: "center",
        alignItems: "center",
        fontWeight: "bold",
        fontSize: "1.3rem",
        color: textColor,
        margin: "2px",
        textTransform: "uppercase",
      }}
    >
      {textSet}
    </div>
  );
}
