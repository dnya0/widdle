import { useEffect } from "react";

type Opts = {
  lang: "kr" | "en";
  onKeyPress: (ch: string) => void;
  onBackspace?: () => void;
  onEnter?: () => void;
};

const MAP_2SET: Record<string, string> = {
  KeyQ: "ㅂ",
  KeyW: "ㅈ",
  KeyE: "ㄷ",
  KeyR: "ㄱ",
  KeyT: "ㅅ",
  KeyY: "ㅛ",
  KeyU: "ㅕ",
  KeyI: "ㅑ",

  KeyA: "ㅁ",
  KeyS: "ㄴ",
  KeyD: "ㅇ",
  KeyF: "ㄹ",
  KeyG: "ㅎ",
  KeyH: "ㅗ",
  KeyJ: "ㅓ",
  KeyK: "ㅏ",
  KeyL: "ㅣ",

  KeyZ: "ㅋ",
  KeyX: "ㅌ",
  KeyC: "ㅊ",
  KeyV: "ㅍ",
  KeyB: "ㅠ",
  KeyN: "ㅜ",
  KeyM: "ㅡ",
};

export const usePhysicalKeyboard = ({
  lang,
  onKeyPress,
  onBackspace,
  onEnter,
}: Opts) => {
  useEffect(() => {
    const onKeyDown = (e: KeyboardEvent) => {
      if (e.isComposing) return;

      if (e.key === "Enter") {
        onEnter?.();
        return;
      }
      if (e.key === "Backspace") {
        onBackspace?.();
        return;
      }

      if (lang === "kr") {
        const mapped = MAP_2SET[e.code];
        if (mapped) {
          onKeyPress(mapped);
          e.preventDefault();
        }
      } else {
        if (/^[a-zA-Z]$/.test(e.key)) {
          onKeyPress(e.key.toUpperCase());
        }
      }
    };

    window.addEventListener("keydown", onKeyDown);
    return () => window.removeEventListener("keydown", onKeyDown);
  }, [lang, onKeyPress, onBackspace, onEnter]);
};
