"use client";

import { useEffect } from "react";

interface HelpModalProps {
  open: boolean;
  onClose: () => void;
}


export default function HelpModal({ open, onClose }: HelpModalProps) {
  useEffect(() => {
    document.body.style.overflow = open ? "hidden" : "auto";
  }, [open]);

  if (!open) return null;

  return (
    <div className="fixed inset-0 z-[9999] flex items-center justify-center bg-black/50">
      <div className="bg-white p-6 rounded-lg shadow-lg w-[320px] text-center relative">
        <button
          onClick={onClose}
          className="absolute top-5 right-6 text-gray-500 hover:text-gray-800"
        >
          ✕
        </button>
        <h2 className="text-lg font-bold mb-4">도움말</h2>
        <p className="mb-2 text-left text-sm">
          ✅ 단어는 자모 단위로 입력됩니다.
          <br />
          ✅ 5자 영어 / 6자 한글 자모 단어를 맞추세요.
          <br />
          ✅ 삭제와 입력은 키보드 또는 버튼을 사용할 수 있습니다.
        </p>
      </div>
    </div>
  );
}