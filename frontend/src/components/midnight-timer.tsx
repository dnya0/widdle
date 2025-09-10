import { useMidnightCountdown } from "@/hooks/use-midnight-countdown";
import { formatSecondsToHHMMSS } from "@/utils/word-utils";

export const MidnightTimer = () => {
  const secondsLeft = useMidnightCountdown();

  return (
    <div style={{ fontFamily: "monospace", fontSize: "1.2rem" }}>
      {formatSecondsToHHMMSS(secondsLeft)}
    </div>
  );
};
