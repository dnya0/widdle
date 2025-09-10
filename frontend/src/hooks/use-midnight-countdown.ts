import { getSecondsUntilMidnight } from "@/utils/word-utils";
import { useEffect, useState } from "react";

export const useMidnightCountdown = () => {
  const [secondsLeft, setSecondsLeft] = useState(getSecondsUntilMidnight());

  useEffect(() => {
    const interval = setInterval(() => {
      setSecondsLeft((prev) => {
        if (prev <= 1) {
          clearInterval(interval);

          const savedDate = localStorage.getItem("lastAccessDate");
          const today = new Date().toISOString().slice(0, 10);

          if (savedDate !== today) {
            localStorage.setItem("lastAccessDate", today);
          }

          return 0; // 카운트다운 멈춤
        }
        return prev - 1;
      });
    }, 1000);

    return () => clearInterval(interval);
  }, []);

  return secondsLeft;
};
