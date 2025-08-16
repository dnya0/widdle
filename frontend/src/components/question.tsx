import Image from "next/image";

export default function QuestionMark({ size = 30 }: { size?: number }) {
  return (
    <Image
      src="/icons/help_outline_46dp_1F1F1F.svg" // 앞에 / 필수
      alt="help"
      width={size}
      height={size}
    />
  );
}
