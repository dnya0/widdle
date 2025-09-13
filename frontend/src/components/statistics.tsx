import { BarChart2 } from "react-feather";

export default function StatisticsIcon({
  size = 2,
  onClick,
}: {
  size?: number;
  onClick?: () => void;
}) {
  return (
    <BarChart2
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
