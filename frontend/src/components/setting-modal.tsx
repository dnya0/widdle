"use client";

import { ModalProps } from "@/utils/word-utils";
import { useEffect } from "react";
import { ChevronRight, X } from "react-feather";

export default function SettingModal({ open, lang, onClose }: ModalProps) {
  useEffect(() => {
    document.body.style.overflow = open ? "hidden" : "auto";
  }, [open]);

  if (!open) return null;

  return (
    <div className="fixed inset-0 z-[9999] flex items-center justify-center bg-gray-800/50">
      <div className="bg-white p-6 rounded-lg shadow-lg w-[400px] text-center relative">
        <div className="relative flex items-center justify-center mb-4">
          <h2 className="text-lg font-bold absolute left-1/2 -translate-x-1/2">
            {lang === "kr" ? "설정" : "Settings"}
          </h2>
          <X
            className="ml-auto text-gray-500 hover:text-gray-800 cursor-pointer"
            onClick={onClose}
          />
        </div>
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
            <ChevronRight />
          </button>
        </div>
      </div>
    </div>
  );
}
