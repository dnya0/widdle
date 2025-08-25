"use client";

import { addWord, WordSaveRequest } from "@/utils/api";
import axios from "axios";
import { useRouter } from "next/navigation";
import { useForm, SubmitHandler } from "react-hook-form";
import toast from "react-hot-toast";

export default function SaveWordPage() {
  const router = useRouter();

  const goToHome = () => {
    router.push("/");
  };

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<WordSaveRequest>();

  const onSubmit: SubmitHandler<WordSaveRequest> = async (data) => {
    try {
      await addWord(data);
      router.push("/");
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        const msg =
          (err.response?.data as { message?: string } | undefined)?.message ||
          err.message ||
          "저장 실패";
        console.error(err.response);
        toast.error(msg);
      } else if (err instanceof Error) {
        toast.error(err.message);
      } else {
        toast.error("Unknown error");
      }
    }
  };

  return (
    <div className="grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen sm:p-20">
      <main className="flex flex-col gap-[10px] row-start-2 items-center sm:items-start">
        <div
          style={{
            display: "flex",
            fontSize: "1.3rem",
            padding: 10,
            alignItems: "center",
            flexDirection: "row",
            justifyContent: "center",
          }}
        >
          <div style={{ margin: 10 }}>단어 등록 폼</div>
        </div>
        <form
          onSubmit={handleSubmit(onSubmit)}
          style={{ fontSize: "1.2rem", marginTop: "8rem" }}
        >
          <div
            style={{
              padding: 10,
              alignItems: "center",
            }}
          >
            <label
              style={{
                margin: 20,
              }}
            >
              Word
              <input
                style={{
                  marginLeft: 50,
                  borderBottom: "1px solid black",
                  fontFamily: "Pretendard-Regular",
                  fontSize: "1rem",
                  padding: 10,
                }}
                {...register("word", { required: "단어는 필수입니다." })}
              />
            </label>
            {errors.word && (
              <p
                style={{
                  fontFamily: "Pretendard-Regular",
                  fontSize: "0.9rem",
                  color: "red",
                  textAlign: "center",
                }}
              >
                {errors.word.message}
              </p>
            )}
          </div>
          <div
            style={{
              marginTop: 30,
              alignItems: "center",
            }}
          >
            <label
              style={{
                margin: 20,
              }}
            >
              Is Korean
              <input
                style={{
                  marginLeft: 20,
                  width: 20,
                  height: 20,
                }}
                type="checkbox"
                {...register("isKorean")}
              />
            </label>
          </div>
          <div
            className="flex gap-4 items-center flex-col sm:flex-row"
            style={{
              marginTop: "12rem",
            }}
          >
            <button
              className="rounded-full border border-solid border-transparent transition-colors flex items-center justify-center bg-foreground text-background gap-2 hover:bg-[#383838] dark:hover:bg-[#ccc] font-medium text-sm sm:text-base h-10 sm:h-12 px-4 sm:px-5 w-full sm:w-auto"
              rel="noopener noreferrer"
              type="submit"
            >
              단어 등록하기
            </button>
            <button
              className="rounded-full border border-solid border-black/[.08] dark:border-white/[.145] transition-colors flex items-center justify-center hover:bg-[#f2f2f2] dark:hover:bg-[#1a1a1a] hover:border-transparent font-medium text-sm sm:text-base h-10 sm:h-12 px-4 sm:px-5 w-full sm:w-auto"
              rel="noopener noreferrer"
              onClick={goToHome}
              type="button"
            >
              취소
            </button>
          </div>
        </form>
      </main>
    </div>
  );
}
