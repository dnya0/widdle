"use client";

import { useEffect } from "react";
import Square from "./alphabet";

interface HelpModalProps {
  open: boolean;
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

export default function HelpModal({ open, onClose }: HelpModalProps) {
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
        <h2 className="text-lg font-bold mb-4">❗️ 도움말 ❗️</h2>
        <p
          className="mb-2 text-left text-sm"
          style={{ fontFamily: "Pretendard-Medium", textAlign: "center" }}
        >
          여섯 개의 자모로 풀어쓴 한글 단어 &quot;위들&quot;을 여섯 번의 도전
          안에 맞혀봅시다.
        </p>
        <div style={{ display: "flex", justifyContent: "center" }}>
          {Array.from({ length: 6 }).map((_, c) => (
            <Square
              key={c}
              text={board[0][c].text}
              colorIndex={board[0][c].colorIndex}
            />
          ))}
        </div>
        <p
          className="mb-2 text-left text-sm"
          style={{
            fontFamily: "Pretendard-Medium",
            marginTop: 10,
            textAlign: "center",
          }}
        >
          &quot;ㄱ&quot;은 올바른 위치에 있습니다.
        </p>
        <div style={{ display: "flex", justifyContent: "center" }}>
          {Array.from({ length: 6 }).map((_, c) => (
            <Square
              key={c}
              text={board[1][c].text}
              colorIndex={board[1][c].colorIndex}
            />
          ))}
        </div>
        <p
          className="mb-2 text-left text-sm"
          style={{
            fontFamily: "Pretendard-Medium",
            marginTop: 10,
            textAlign: "center",
          }}
        >
          모음 &quot;ㅜ&quot;는 단어에 포함되어 있지만 잘못된 위치에 있습니다.
        </p>
        <div style={{ display: "flex", justifyContent: "center" }}>
          {Array.from({ length: 6 }).map((_, c) => (
            <Square
              key={c}
              text={board[2][c].text}
              colorIndex={board[2][c].colorIndex}
            />
          ))}
        </div>
        <p
          className="mb-2 text-left text-sm"
          style={{
            fontFamily: "Pretendard-Medium",
            marginTop: 10,
            textAlign: "center",
          }}
        >
          자음 &quot;ㄴ&quot;은 단어에 포함되있지 않습니다.<br/>
          위들은 자정에 초기화됩니다.
        </p>
      </div>
    </div>
  );
}
