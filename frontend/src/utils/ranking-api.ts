import { api } from "./api";
import type { RankingDashboardResponse } from "@/types/ranking";

export async function getRankingDashboard(): Promise<RankingDashboardResponse> {
  const { data } =
    await api.get<RankingDashboardResponse>("/ranking/dashboard");
  return data;
}

export function formatSecondsToDurationLabel(seconds: number): string {
  if (Number.isNaN(seconds) || seconds < 0) {
    return "--:--";
  }
  const totalSeconds = Math.floor(seconds);
  const minutes = Math.floor(totalSeconds / 60)
    .toString()
    .padStart(2, "0");
  const secs = (totalSeconds % 60).toString().padStart(2, "0");
  return `${minutes}:${secs}`;
}
