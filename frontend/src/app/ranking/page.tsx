"use client";

import Link from "next/link";
import {
  RankingDashboardSkeleton,
  RankingHighlight,
  RecentActivity,
  SummaryCards,
} from "@/components/ranking";
import { useRankingDashboard } from "@/hooks/use-ranking-dashboard";

export default function RankingDashboardPage() {
  const { loading, error, refresh, viewModel } = useRankingDashboard();

  return (
    <div className="mx-auto flex w-full max-w-5xl flex-col gap-6 pb-16">
      <header className="flex flex-col gap-2 py-6">
        <p className="text-sm font-medium text-indigo-600">랭킹 대시보드</p>
        <h1 className="text-3xl font-semibold text-slate-900">
          다양한 기준의 경쟁 랭킹
        </h1>
        <p className="text-sm text-slate-500">
          서비스 내 플레이 데이터를 한눈에 확인하고 재도전을 유도하세요.
        </p>
      </header>

      {loading && <RankingDashboardSkeleton />}

      {!loading && error && (
        <div className="rounded-2xl border border-red-100 bg-red-50 p-4 text-sm text-red-700">
          {error}{" "}
          <button
            className="ml-4 font-semibold underline"
            onClick={() => refresh()}
          >
            다시 시도
          </button>
        </div>
      )}

      {!loading && viewModel && (
        <>
          <SummaryCards cards={viewModel.summaryCards} />
          <RankingHighlight {...viewModel.ranking} />
          <RecentActivity data={viewModel.recentPlays} />

          <section className="rounded-3xl border border-slate-200 bg-slate-50/80 p-6 text-center">
            <p className="text-sm text-slate-500">
              더 많은 기준의 순위 변화를 확인하고 싶다면?
            </p>
            <Link
              href="/ranking/history"
              className="mt-3 inline-flex items-center justify-center rounded-full bg-slate-900 px-6 py-3 text-sm font-medium text-white transition hover:bg-slate-700"
            >
              랭킹 상세 보기
            </Link>
            <p className="mt-2 text-xs text-slate-400">
              해당 버튼은 일별 히스토리 확장 시 활용됩니다.
            </p>
          </section>
        </>
      )}
    </div>
  );
}
