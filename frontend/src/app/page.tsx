"use client";

import { useRouter } from "next/navigation";
import { TypeAnimation } from "react-type-animation";

export default function Home() {
  const router = useRouter();
  const goToKRPage = () => {
    router.push("/kr"); // /kr 경로로 이동
  };

  const goToEngPage = () => {
    router.push("/en");
  };

  const goToWordAddPage = () => {
    router.push("/add");
  };

  return (
    <div className="grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen sm:p-20">
      <main className="flex flex-col gap-[10px] row-start-2 items-center sm:items-start">
        <TypeAnimation
          sequence={["Widdle", 5000, "위들", 4600]}
          wrapper="span"
          speed={1}
          repeat={Infinity}
          style={{
            fontSize: "2.5rem",
            fontFamily: "Pretendard-SemiBold",
          }}
        />
        <TypeAnimation
          sequence={[
            "Start the word puzzle on one site.",
            1000,
            "단어 퍼즐을 한 사이트에서 시작하세요.",
            1200,
          ]}
          wrapper="span"
          speed={20}
          repeat={Infinity}
          style={{
            fontSize: "1.2rem",
            fontFamily: "Pretendard-Regular",
          }}
        />
        <div
          className="flex gap-4 items-center flex-col sm:flex-row"
          style={{
            marginTop: "14rem",
          }}
        >
          <button
            className="rounded-full border border-solid border-transparent transition-colors flex items-center justify-center bg-foreground text-background gap-2 hover:bg-[#383838] dark:hover:bg-[#ccc] font-medium text-sm sm:text-base h-10 sm:h-12 px-4 sm:px-5 sm:w-auto"
            rel="noopener noreferrer"
            onClick={goToKRPage}
          >
            한국어로 시작하기
          </button>
          <button
            className="rounded-full border border-solid border-black/[.08] dark:border-white/[.145] transition-colors flex items-center justify-center hover:bg-[#f2f2f2] dark:hover:bg-[#1a1a1a] hover:border-transparent font-medium text-sm sm:text-base h-10 sm:h-12 px-4 sm:px-5 w-full sm:w-auto"
            rel="noopener noreferrer"
            onClick={goToEngPage}
          >
            Starting in English
          </button>
        </div>
        <div
          className="w-full flex justify-center mt-[30px] self-center"
          style={{
            display: "flex",
            justifyContent: "center",
            textAlign: "center",
          }}
        >
          <a
            style={{
              fontSize: "0.8rem",
              fontFamily: "Pretendard-Regular",
              textDecoration: "underline",
              textUnderlineOffset: "4px",
              cursor: "pointer",
              alignSelf: "center",
            }}
            onClick={goToWordAddPage}
          >
            단어 추가하기
          </a>
        </div>
      </main>
    </div>
  );
}
