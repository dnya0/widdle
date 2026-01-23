import { useCallback, useEffect, useMemo, useState } from "react";
import {
  type RankingDashboardResponse,
  type RankingEntry,
  type RecentPlay,
  RankingType,
} from "@/types/ranking";
import {
  formatSecondsToDurationLabel,
  getRankingDashboard,
} from "@/utils/ranking-api";

type RankingMetadata = {
  title: string;
  subtitle: string;
  scopeTag: "오늘 기준" | "누적 기준" | "성장 지표";
  metricLabel: string;
};

type PodiumEntry = RankingEntry & { highlightColor: string };

export type RankingDashboardViewModel = {
  summaryCards: {
    label: string;
    value: string;
    helperText?: string;
  }[];
  ranking: {
    metadata: RankingMetadata;
    podium: PodiumEntry[];
  };
  recentPlays: RecentPlay[];
  updatedAtLabel: string;
};

const RANKING_METADATA: Record<RankingType, RankingMetadata> = {
  [RankingType.TODAY_CHAMPION]: {
    title: "오늘의 챔피언",
    subtitle: "종합 점수 기반 대표 랭킹",
    scopeTag: "오늘 기준",
    metricLabel: "종합 점수",
  },
  [RankingType.SPEED_KING]: {
    title: "스피드 킹",
    subtitle: "최단 플레이 타임 (성공률 80% 이상)",
    scopeTag: "오늘 기준",
    metricLabel: "평균 시간",
  },
  [RankingType.STREAK_MASTER]: {
    title: "연속 성공 랭킹",
    subtitle: "current_streak 내림차순",
    scopeTag: "누적 기준",
    metricLabel: "연속 기록",
  },
  [RankingType.GROWTH_MASTER]: {
    title: "성장왕",
    subtitle: "전일 대비 향상 지표",
    scopeTag: "성장 지표",
    metricLabel: "증가량",
  },
};

const PODIUM_COLORS = ["#FACC15", "#E5E7EB", "#F97316"];

const UPDATED_AT_FALLBACK = "업데이트 시간 정보 없음";

function attachDefaultRank(entries: RankingEntry[]): PodiumEntry[] {
  const normalized = [...entries].sort((a, b) => a.rank - b.rank).slice(0, 3);

  const filled = Array.from({ length: 3 }).map((_, idx) => {
    const data = normalized[idx];
    if (!data) {
      return {
        rank: idx + 1,
        nickname: "데이터 없음",
        value: "--",
        highlightColor: PODIUM_COLORS[idx],
      } as PodiumEntry;
    }
    return {
      ...data,
      highlightColor: PODIUM_COLORS[idx],
    };
  });

  return filled;
}

export function useRankingDashboard() {
  const [data, setData] = useState<RankingDashboardResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchData = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await getRankingDashboard();
      setData(response);
    } catch (err) {
      setError("랭킹 정보를 불러오지 못했어요.");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    void fetchData();
  }, [fetchData]);

  const viewModel = useMemo<RankingDashboardViewModel | null>(() => {
    if (!data) return null;
    const metadata =
      RANKING_METADATA[data.rankingType] ??
      ({
        title: "랭킹",
        subtitle: "대표 랭킹",
        scopeTag: "오늘 기준",
        metricLabel: "값",
      } as RankingMetadata);

    const summaryCards = [
      {
        label: "총 참가자 수",
        value: data.summary.totalUser.toLocaleString(),
        helperText: "참여한 전체 사용자",
      },
      {
        label: "오늘 평균 플레이 시간",
        value: formatSecondsToDurationLabel(data.summary.avgPlaytime),
        helperText: "mm:ss",
      },
    ];

    const podium = attachDefaultRank(data.top3);

    const recentPlays = [...data.recent].sort((a, b) => {
      if (!a.modifiedAt || !b.modifiedAt) return 0;
      return (
        new Date(b.modifiedAt).getTime() - new Date(a.modifiedAt).getTime()
      );
    });

    const updatedAtLabel = data.updatedAt
      ? new Date(data.updatedAt).toLocaleString("ko-KR", {
          month: "long",
          day: "numeric",
          hour: "2-digit",
          minute: "2-digit",
        })
      : UPDATED_AT_FALLBACK;

    return {
      summaryCards,
      ranking: {
        metadata,
        podium,
      },
      recentPlays,
      updatedAtLabel,
    };
  }, [data]);

  return {
    loading,
    error,
    refresh: fetchData,
    data,
    viewModel,
  };
}
