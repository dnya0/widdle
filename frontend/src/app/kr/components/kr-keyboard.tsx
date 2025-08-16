"use client";

import Keyboard from "@/components/keyboard";

export default function KoreanKeyboard({
  onKeyPress,
  onBackspace,
  onEnter,
}: {
  onKeyPress: (ch: string) => void;
  onBackspace?: () => void;
  onEnter?: () => void;
}) {
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
          <Keyboard key={ch} text={ch} onClick={() => onKeyPress(ch)} />
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
          <Keyboard key={ch} text={ch} onClick={() => onKeyPress(ch)} />
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
            widthSize={30}
            onClick={() => onKeyPress(ch)}
          />
        ))}
        <Keyboard text="삭제" widthSize={60} onClick={() => onBackspace?.()} />
      </div>
    </div>
  );
}
