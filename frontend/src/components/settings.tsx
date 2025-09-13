import { Settings } from "react-feather";

export default function SettingsIcon({
  size = 1,
  onClick,
}: {
  size?: number;
  onClick?: () => void;
}) {
  return (
    <Settings
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
