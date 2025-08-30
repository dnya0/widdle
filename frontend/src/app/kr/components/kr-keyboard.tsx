"use client";

import Keyboard from "@/components/keyboard";
import { usePhysicalKeyboard } from "@/hooks/use-key-handler";

export default function KoreanKeyboard({
  onKeyPress,
  onBackspace,
  onEnter,
  keyColors = {},
}: {
  onKeyPress: (ch: string) => void;
  onBackspace?: () => void;
  onEnter?: () => void;
  keyColors?: Record<string, number>;
}) {
  usePhysicalKeyboard({ lang: "kr", onKeyPress, onBackspace, onEnter });
  return (
    <div style={{ display: "flex", flexDirection: "column" }}>
      {/* 1열 */}
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        {["ㅂ", "ㅈ", "ㄷ", "ㄱ", "ㅅ", "ㅛ", "ㅕ", "ㅑ"].map((ch) => (
          <Keyboard
            key={ch}
            text={ch}
            colorIndex={keyColors[ch] ?? 0}
            onClick={() => onKeyPress(ch)}
          />
        ))}
      </div>

      {/* 2열 */}
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        {["ㅁ", "ㄴ", "ㅇ", "ㄹ", "ㅎ", "ㅗ", "ㅓ", "ㅏ", "ㅣ"].map((ch) => (
          <Keyboard
            key={ch}
            text={ch}
            colorIndex={keyColors[ch] ?? 0}
            onClick={() => onKeyPress(ch)}
          />
        ))}
      </div>

      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <Keyboard
          text="입력"
          widthSize={60}
          onClick={() => {
            console.log("[Keyboard] enter clicked");
            onEnter?.();
          }}
        />

        {["ㅋ", "ㅌ", "ㅊ", "ㅍ", "ㅠ", "ㅜ", "ㅡ"].map((ch) => (
          <Keyboard
            key={ch}
            text={ch}
            colorIndex={keyColors[ch] ?? 0}
            widthSize={30}
            onClick={() => onKeyPress(ch)}
          />
        ))}
        <Keyboard text="삭제" widthSize={60} onClick={() => onBackspace?.()} />
      </div>
    </div>
  );
}
