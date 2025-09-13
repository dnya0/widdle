"use client";

import Keyboard from "@/components/keyboard";
import { usePhysicalKeyboard } from "@/hooks/use-key-handler";

export default function EnglishKeyboard({
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
  usePhysicalKeyboard({ lang: "en", onKeyPress, onBackspace, onEnter });
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
        {["Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"].map((ch) => (
          <Keyboard
            key={ch}
            text={ch}
            widthSize={2}
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
        {["A", "S", "D", "F", "G", "H", "J", "K", "L"].map((ch) => (
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
          text="Enter"
          widthSize={5}
          onClick={() => {
            console.log("[Keyboard] enter clicked");
            onEnter?.();
          }}
        />

        {["Z", "X", "C", "V", "B", "N", "M"].map((ch) => (
          <Keyboard
            key={ch}
            text={ch}
            colorIndex={keyColors[ch] ?? 0}
            widthSize={1}
            onClick={() => onKeyPress(ch)}
          />
        ))}
        <Keyboard
          text="Delete"
          widthSize={5}
          onClick={() => onBackspace?.()}
        />
      </div>
    </div>
  );
}
