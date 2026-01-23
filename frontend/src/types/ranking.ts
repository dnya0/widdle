export type RankingSummary = {
  totalUser: number;
  avgPlaytime: number; // seconds
};

export type RankingEntry = {
  rank: number;
  nickname: string;
  value: string;
  isSelf?: boolean;
  successRate?: number;
  todayPlaytime?: number;
  currentStreak?: number;
  bestStreak?: number;
  growthDelta?: number;
};

export type RecentPlay = {
  nickname: string;
  value: string;
  modifiedAt?: string;
};

export enum RankingType {
  TODAY_CHAMPION = "TODAY_CHAMPION",
  SPEED_KING = "SPEED_KING",
  STREAK_MASTER = "STREAK_MASTER",
  GROWTH_MASTER = "GROWTH_MASTER",
}

export type RankingDashboardResponse = {
  summary: RankingSummary;
  rankingType: RankingType;
  top3: RankingEntry[];
  recent: RecentPlay[];
  updatedAt?: string;
};
