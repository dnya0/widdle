"use client";

import { ModalProps } from "@/utils/word-utils";
import { useEffect } from "react";

export default function SettingModal({ open, lang, onClose }: ModalProps) {
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
              window.open("https://github.com/dnya0/widdle/issues", "_blank")
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
