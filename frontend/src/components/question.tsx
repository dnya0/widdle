import { HelpCircle } from "react-feather";

export default function QuestionMark({
  size = 2,
  onClick,
}: {
  size?: number;
  onClick?: () => void;
}) {
  return (
    <HelpCircle
      onClick={onClick}
      style={{
        width: `${size}vw`,
        height: `${size}vw`,
        minWidth: 24,
        minHeight: 24,
        cursor: "pointer",
      }}
    />
  );
}
