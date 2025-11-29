"use client";

import { ModalProps } from "@/utils/word-utils";
import { useEffect, useState } from "react"; // useState 추가
import { ChevronRight, X } from "react-feather";

// 뷰 상태를 정의하는 타입
type ModalView = "main" | "license";

export default function SettingModal({ open, lang, onClose }: ModalProps) {
  // 현재 모달이 어떤 내용을 보여줄지 결정하는 상태 (기본값: 'main')
  const [view, setView] = useState<ModalView>("main");

  useEffect(() => {
    document.body.style.overflow = open ? "hidden" : "auto";

    // 모달이 닫힐 때 view 상태도 'main'으로 초기화
    if (!open) {
      setView("main");
    }
  }, [open]);

  if (!open) return null;

  // --- 1. 라이선스 고지 화면 컴포넌트 ---
  const LicenseView = (
    <>
      <div className="relative flex items-center justify-start mb-4">
        {/* 뒤로 가기 버튼: view 상태를 'main'으로 변경 */}
        <button
          onClick={() => setView("main")}
          className="text-gray-500 hover:text-gray-800 cursor-pointer absolute left-0"
          aria-label={lang === "kr" ? "뒤로 가기" : "Back"}
        >
          <ChevronRight style={{ transform: "rotate(180deg)" }} />
        </button>
        <h2 className="text-lg font-bold absolute left-1/2 -translate-x-1/2">
          {lang === "kr" ? "라이선스 정보" : "Licensing Info"}
        </h2>
        <X
          className="ml-auto text-gray-500 hover:text-gray-800 cursor-pointer"
          onClick={onClose}
        />
      </div>

      <div className="text-sm text-left p-2 border border-gray-200 rounded-md">
        {/* === 1. 단어 존재 여부 (표준국어대사전) === */}
        <h3 className="font-semibold mb-2">
          {lang === "kr"
            ? "단어 데이터 출처 및 라이선스"
            : "Word Data Source & Licensing"}
        </h3>
        <p className="mb-1">
          {lang === "kr"
            ? "본 서비스의 단어 존재 여부 확인 기능은 국립국어원 표준국어대사전 Open API를 통해 제공됩니다."
            : "Word existence checks are provided via the Open API of the National Institute of Korean Language (Standard Korean Dictionary)."}
        </p>
        <p className="mb-4 text-xs text-gray-500">
          {lang === "kr"
            ? "해당 자료는 CC BY-SA 2.0 KR 라이선스에 따라 이용됩니다."
            : "The data is used under CC BY-SA 2.0 Korea License."}
        </p>

        <hr className="my-3 border-gray-300" />

        {/* === 2. 형태소 분석/교정 기능 (바른 API) === */}
        <h3 className="font-semibold mb-2">
          {lang === "kr"
            ? "텍스트 분석 기능 출처"
            : "Text Analysis Feature Source"}
        </h3>
        <p className="mb-1">
          {lang === "kr"
            ? "서비스 내 형태소 분석 및 교정 기능은 ㈜바이칼에이아이의 '바른 형태소 분석기 및 교정기'를 이용하여 제공됩니다."
            : "Morphological analysis and correction features are provided using 'Bareun Morphological Analyzer and Corrector' by Baikal AI Inc."}
        </p>
        <p className="text-xs text-red-500 mt-2">
          {/* 약관 제9조 고지 */}
          {lang === "kr"
            ? "⚠️ 사용자가 입력하는 텍스트(콘텐츠)는 서비스 제공, 기능 개선 및 통계 분석 목적으로 ㈜바이칼에이아이 측에 전송 및 처리될 수 있습니다. (이용약관 제9조)"
            : "⚠️ The text entered by the user (Content) may be transmitted to and processed by Baikal AI Inc. for the purpose of service provision, feature improvement, and statistical analysis. (Terms of Service, Article 9)"}
        </p>
      </div>
    </>
  );

  // --- 2. 메인 설정 목록 화면 컴포넌트 ---
  const MainView = (
    <>
      <div className="relative flex items-center justify-center mb-4">
        <h2 className="text-lg font-bold absolute left-1/2 -translate-x-1/2">
          {lang === "kr" ? "설정" : "Settings"}
        </h2>
        <X
          className="ml-auto text-gray-500 hover:text-gray-800 cursor-pointer"
          onClick={onClose}
        />
      </div>

      {/* 문의하기 버튼 */}
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

      {/* 이용 약관 버튼 */}
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
          }}
          onClick={() => setView("license")} // <--- 클릭 시 'license' 뷰로 전환
        >
          <div>{lang == "kr" ? "라이선스 정보" : "Licensing Info"}</div>
          <ChevronRight />
        </button>
      </div>
    </>
  );

  return (
    <div className="fixed inset-0 z-[9999] flex items-center justify-center bg-gray-800/50">
      <div className="bg-white p-6 rounded-lg shadow-lg w-[400px] text-center relative">
        {/* view 상태에 따라 렌더링할 내용을 분기 */}
        {view === "main" && MainView}
        {view === "license" && LicenseView}
      </div>
    </div>
  );
}
