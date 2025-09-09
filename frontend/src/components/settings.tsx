import Image from "next/image";

export default function SettingsIcon({
  size = 30,
  onClick,
}: {
  size?: number;
  onClick?: () => void;
}) {
  return (
    <Image
      src="/icons/settings_24dp_000000.svg" // 앞에 / 필수
      alt="help"
      width={size}
      height={size}
      onClick={onClick}
    />
  );
}
