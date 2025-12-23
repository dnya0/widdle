"use client";

import { ModalProps } from "@/utils/word-utils";
import { useEffect, useState } from "react";
import { ChevronRight, X } from "react-feather";

type ModalView = "main" | "license";

export default function SettingModal({ open, lang, onClose }: ModalProps) {
  const [view, setView] = useState<ModalView>("main");

  useEffect(() => {
    document.body.style.overflow = open ? "hidden" : "auto";

    if (!open) {
      setView("main");
    }
  }, [open]);

  if (!open) return null;

  // --- 1. 라이선스 고지 화면 컴포넌트 ---
  const LicenseView = (
    <>
      <div className="relative flex items-center justify-start mb-4 text-black">
        <button
          onClick={() => setView("main")}
          className="text-black hover:text-black cursor-pointer absolute left-0"
          aria-label={lang === "kr" ? "뒤로 가기" : "Back"}
        >
          <ChevronRight style={{ transform: "rotate(180deg)" }} />
        </button>
        <h2
          className="text-lg font-bold absolute left-1/2 -translate-x-1/2"
          style={{ color: "black" }}
        >
          {lang === "kr" ? "라이선스 정보" : "Licensing Info"}
        </h2>
        <X
          className="ml-auto text-black hover:text-black cursor-pointer"
          onClick={onClose}
        />
      </div>

      <div className="text-sm text-left p-2 border border-gray-200 rounded-md text-black">
        <h3 className="font-semibold mb-2" style={{ color: "black" }}>
          {lang === "kr"
            ? "단어 데이터 출처 및 라이선스"
            : "Word Data Source & Licensing"}
        </h3>
        <p className="mb-1" style={{ color: "black" }}>
          {lang === "kr"
            ? "본 서비스의 단어 존재 여부 확인 기능은 국립국어원 표준국어대사전 Open API를 통해 제공됩니다."
            : "Word existence checks are provided via the Open API of the National Institute of Korean Language (Standard Korean Dictionary)."}
        </p>
        <p className="mb-4 text-xs" style={{ color: "black" }}>
          {lang === "kr"
            ? "해당 자료는 CC BY-SA 2.0 KR 라이선스에 따라 이용됩니다."
            : "The data is used under CC BY-SA 2.0 Korea License."}
        </p>

        <hr className="my-3 border-gray-300" />

        <h3 className="font-semibold mb-2" style={{ color: "black" }}>
          {lang === "kr"
            ? "텍스트 분석 기능 출처"
            : "Text Analysis Feature Source"}
        </h3>
        <p className="mb-1" style={{ color: "black" }}>
          {lang === "kr"
            ? "서비스 내 형태소 분석 및 교정 기능은 ㈜바이칼에이아이의 '바른 형태소 분석기 및 교정기'를 이용하여 제공됩니다."
            : "Morphological analysis and correction features are provided using 'Bareun Morphological Analyzer and Corrector' by Baikal AI Inc."}
        </p>
        <p className="text-xs mt-2" style={{ color: "black" }}>
          {lang === "kr"
            ? "⚠️ 사용자가 입력하는 텍스트(콘텐츠)는 서비스 제공, 기능 개선 및 통계 분석 목적으로 ㈜바이칼에이아이 측에 전송 및 처리될 수 있습니다. (이용약관 제9조)"
            : "⚠️ The text entered by the user (Content) may be transmitted to and processed by Baikal AI Inc. for the purpose of service provision, feature improvement, and statistical analysis. (Terms of Service, Article 9)"}
        </p>

        <hr className="my-3 border-gray-300" />

        <h3 className="font-semibold mb-2" style={{ color: "black" }}>
          {lang === "kr" ? "영단어 데이터 출처" : "English Word Data Source"}
        </h3>
        <p className="mb-1" style={{ color: "black" }}>
          {lang === "kr"
            ? "영단어 검색 결과 및 정의 데이터는 Merriam-Webster API를 통해 제공됩니다."
            : "English word search results and definition data are provided via the Merriam-Webster API."}
        </p>
        <p className="mb-4 text-xs" style={{ color: "black" }}>
          {lang === "kr"
            ? "저작권 고지: Data supplied by Merriam-Webster"
            : "Copyright Notice: Data supplied by Merriam-Webster"}
        </p>
        <p className="text-xs mt-2">
          <a
            href="https://www.merriam-webster.com/"
            target="_blank"
            rel="noopener noreferrer"
            className="text-blue-600 hover:underline ml-1"
          >
            (
            {lang === "kr"
              ? "Merriam-Webster 웹사이트"
              : "Merriam-Webster Website"}
            )
          </a>
        </p>
      </div>
    </>
  );

  // --- 2. 메인 설정 목록 화면 컴포넌트 ---
  const MainView = (
    <>
      <div className="relative flex items-center justify-center mb-4 text-black">
        <h2
          className="text-lg font-bold absolute left-1/2 -translate-x-1/2"
          style={{ color: "black" }}
        >
          {lang === "kr" ? "설정" : "Settings"}
        </h2>
        <X
          className="ml-auto text-black hover:text-black cursor-pointer"
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
            color: "black", // 글자색 추가
          }}
          onClick={() =>
            window.open("https://github.com/dnya0/widdle/issues", "_blank")
          }
        >
          <div style={{ color: "black" }}>
            {lang == "kr" ? "문의하기" : "Contact us"}
          </div>
          <ChevronRight />
        </button>
      </div>

      <div style={{ display: "flex", justifyContent: "center" }}>
        <button
          style={{
            display: "flex",
            fontFamily: "Pretendard-Medium",
            width: "100%",
            fontSize: "1rem",
            padding: 12,
            borderBottom: "1px solid",
            justifyContent: "space-between",
            cursor: "pointer",
            color: "black", // 글자색 추가
          }}
          onClick={() => setView("license")}
        >
          <div style={{ color: "black" }}>
            {lang == "kr" ? "라이선스 정보" : "Licensing Info"}
          </div>
          <ChevronRight />
        </button>
      </div>
    </>
  );

  return (
    <div className="fixed inset-0 z-[9999] flex items-center justify-center bg-gray-800/50">
      <div className="bg-white p-6 rounded-lg shadow-lg w-[400px] text-center relative">
        {view === "main" && MainView}
        {view === "license" && LicenseView}
      </div>
    </div>
  );
}
