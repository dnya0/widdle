import type { RankingDashboardViewModel } from "@/hooks/use-ranking-dashboard";
import { Badge } from "./badge";

type RankingHighlightProps = RankingDashboardViewModel["ranking"];

export function RankingHighlight({ metadata, podium }: RankingHighlightProps) {
  return (
    <section className="rounded-3xl border border-slate-200 bg-gradient-to-br from-slate-50 to-white p-6 shadow-inner">
      <div className="flex flex-wrap items-center justify-between gap-3">
        <div>
          <Badge
            label={metadata.scopeTag}
            variant="outline"
            color="#CBD5F5"
            className="text-slate-600"
          />
          <h2 className="mt-3 text-2xl font-semibold text-slate-900">
            {metadata.title}
          </h2>
          <p className="text-sm text-slate-500">{metadata.subtitle}</p>
        </div>
        <Badge
          label={metadata.metricLabel}
          variant="solid"
          color="#FDE68A"
          className="text-slate-900"
        />
      </div>

      <div className="mt-8 grid gap-4 sm:grid-cols-3">
        {podium.map((entry) => (
          <article
            key={entry.rank}
            className={`rounded-2xl border p-5 text-center transition-all ${
              entry.isSelf ? "border-indigo-500 shadow-md" : "border-slate-200"
            }`}
            style={{ backgroundColor: `${entry.highlightColor}14` }}
          >
            <div className="mx-auto flex h-10 w-10 items-center justify-center rounded-full text-lg font-semibold text-slate-900">
              #{entry.rank}
            </div>
            <p className="mt-3 text-lg font-semibold text-slate-900">
              {entry.nickname}
            </p>
            <p className="text-sm text-slate-600">{entry.value}</p>
          </article>
        ))}
      </div>
    </section>
  );
}
