"use client";

import { useEffect } from "react";
import Square from "./alphabet";

interface HelpModalProps {
  open: boolean;
  lang: "kr" | "en";
  onClose: () => void;
}

type Cell = {
  text: string;
  colorIndex?: number;
};

const board: Cell[][] = [
  [
    { text: "ㄱ", colorIndex: 3 },
    { text: "ㅕ", colorIndex: undefined },
    { text: "ㅣ", colorIndex: undefined },
    { text: "ㅈ", colorIndex: undefined },
    { text: "ㅓ", colorIndex: undefined },
    { text: "ㅇ", colorIndex: undefined },
  ],
  [
    { text: "ㅇ", colorIndex: undefined },
    { text: "ㅜ", colorIndex: 2 },
    { text: "ㅣ", colorIndex: undefined },
    { text: "ㅅ", colorIndex: undefined },
    { text: "ㅏ", colorIndex: undefined },
    { text: "ㅇ", colorIndex: undefined },
  ],
  [
    { text: "ㅎ", colorIndex: undefined },
    { text: "ㅗ", colorIndex: undefined },
    { text: "ㅣ", colorIndex: undefined },
    { text: "ㅈ", colorIndex: undefined },
    { text: "ㅓ", colorIndex: undefined },
    { text: "ㄴ", colorIndex: 1 },
  ],
];

const board_en: Cell[][] = [
  [
    { text: "a", colorIndex: 3 },
    { text: "p", colorIndex: undefined },
    { text: "p", colorIndex: undefined },
    { text: "l", colorIndex: undefined },
    { text: "e", colorIndex: undefined },
  ],
  [
    { text: "c", colorIndex: undefined },
    { text: "o", colorIndex: 2 },
    { text: "u", colorIndex: undefined },
    { text: "n", colorIndex: undefined },
    { text: "t", colorIndex: undefined },
  ],
  [
    { text: "t", colorIndex: undefined },
    { text: "r", colorIndex: undefined },
    { text: "i", colorIndex: undefined },
    { text: "c", colorIndex: undefined },
    { text: "k", colorIndex: 1 },
  ],
];

export default function SettingModal({ open, lang, onClose }: HelpModalProps) {
  useEffect(() => {
    document.body.style.overflow = open ? "hidden" : "auto";
  }, [open]);

  if (!open) return null;

  return (
    <div className="fixed inset-0 z-[9999] flex items-center justify-center bg-gray-800/50">
      <div className="bg-white p-6 rounded-lg shadow-lg w-[400px] text-center relative">
        <button
          onClick={onClose}
          className="absolute top-5 right-6 text-gray-500 hover:text-gray-800"
        >
          ✕
        </button>
        <h2 className="text-lg font-bold mb-4">
          {lang == "kr" ? "설정" : "Settings"}
        </h2>
        <div style={{ display: "flex", justifyContent: "center" }}>
          <button
            style={{
              display: "flex",
              fontFamily: "Pretendard-Medium",
              width: "100%",
              fontSize: "1rem",
              padding: 12,
              borderTop: "1px solid",
              borderBottom: "1px solid",
              justifyContent: "space-between",
              cursor: "pointer",
            }}
            onClick={() =>
              window.open("https://github.com/ahma0/widdle/issues", "_blank")
            }
          >
            <div>{lang == "kr" ? "문의하기" : "Contact us"}</div>
            <div>{">"}</div>
          </button>
        </div>
      </div>
    </div>
  );
}
