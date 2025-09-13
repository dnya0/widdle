import { BarChart2 } from "react-feather";

export default function StatisticsIcon({
  size = 1,
  onClick,
}: {
  size?: number;
  onClick?: () => void;
}) {
  return (
    <BarChart2
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
