"use client";

import { useEffect } from "react";
import Square from "./alphabet";
import { ModalProps } from "@/utils/word-utils";
import { X } from "react-feather";

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

export default function HelpModal({ open, lang, onClose }: ModalProps) {
  useEffect(() => {
    document.body.style.overflow = open ? "hidden" : "auto";
  }, [open]);

  if (!open) return null;

  return (
    <div className="fixed inset-0 z-[9999] flex items-center justify-center bg-gray-800/50">
      <div className="bg-white p-6 rounded-lg shadow-lg w-[400px] text-center relative">
        <div className="relative flex items-center justify-center mb-4">
          <h2 className="text-lg font-bold absolute left-1/2 -translate-x-1/2">
            {lang == "kr" ? "❗️ 도움말 ❗️" : "❗️ How To Play ❗️"}
          </h2>
          <X
            className="ml-auto text-gray-500 hover:text-gray-800 cursor-pointer"
            onClick={onClose}
          />
        </div>
        <p
          className="mb-2 text-left text-sm"
          style={{ fontFamily: "Pretendard-Medium", textAlign: "center" }}
        >
          {lang == "kr"
            ? '여섯 개의 자모로 풀어쓴 한글 단어 "위들"을 여섯 번의 도전안에 맞혀봅시다.'
            : 'Let\'s guess the English word "Widdle" with five letters in six attempts.'}
        </p>
        <div style={{ display: "flex", justifyContent: "center" }}>
          {(lang === "kr" ? board[0] : board_en[0]).map((cell, c) => (
            <Square key={c} text={cell.text} colorIndex={cell.colorIndex} />
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
          {lang == "kr"
            ? '자음 "ㄱ"은 올바른 위치에 있습니다.'
            : '"A" is in the correct position.'}
        </p>
        <div style={{ display: "flex", justifyContent: "center" }}>
          {(lang === "kr" ? board[1] : board_en[1]).map((cell, c) => (
            <Square key={c} text={cell.text} colorIndex={cell.colorIndex} />
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
          {lang == "kr"
            ? '모음 "ㅜ"는 단어에 포함되어 있지만 잘못된 위치에 있습니다.'
            : '"O" is included in the word but is in the wrong position.'}
        </p>
        <div style={{ display: "flex", justifyContent: "center" }}>
          {(lang === "kr" ? board[2] : board_en[2]).map((cell, c) => (
            <Square key={c} text={cell.text} colorIndex={cell.colorIndex} />
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
          {lang == "kr"
            ? '자음 "ㄴ"은 단어에 포함되있지 않습니다.'
            : '"K" is not included in the word.'}

          <br />
          <br />
          <br />

          {lang == "kr"
            ? "위들은 자정에 초기화됩니다."
            : "The above are initialized at midnight."}
        </p>
      </div>
    </div>
  );
}
