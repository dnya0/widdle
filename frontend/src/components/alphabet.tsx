type SquareProps = {
  size?: number; // 네모 크기
  colorIndex?: number;
  text?: string;
};

const colors = ["#fff", "rgba(238, 190, 0, 1)", "rgba(17, 198, 0, 1)", "#a4a4a4ff"];

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

export default function Square({ size = 54, colorIndex, text }: SquareProps) {
  const backgroundColor =
    colorIndex !== undefined && colors[colorIndex]
      ? colors[colorIndex]
      : "#fff";
  const borderColor = getBorderColor({ colorIndex, text });
  const textColor =
    colorIndex !== undefined && colors[colorIndex] ? "#fff" : "#000";
  const textSet = text === undefined || text === null ? " " : text;

  return (
    <div
      style={{
        width: size,
        height: size,
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
