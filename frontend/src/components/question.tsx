import { HelpCircle } from "react-feather";

export default function QuestionMark({
  size = 1,
  onClick,
}: {
  size?: number;
  onClick?: () => void;
}) {
  return (
    <HelpCircle
      onClick={onClick}
      style={{
        width: `${size}rem`,
        height: `${size}rem`,
        minWidth: 24,
        minHeight: 24,
        cursor: "pointer",
      }}
    />
  );
}
