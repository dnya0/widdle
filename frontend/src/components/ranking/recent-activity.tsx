import type { RecentPlay } from "@/types/ranking";

type RecentActivityProps = {
  data: RecentPlay[];
};

const formatter = new Intl.DateTimeFormat("ko-KR", {
  hour: "2-digit",
  minute: "2-digit",
});

export function RecentActivity({ data }: RecentActivityProps) {
  return (
    <section className="rounded-3xl border border-slate-200 bg-white/90 p-6 shadow-sm">
      <header className="flex items-center justify-between">
        <div>
          <p className="text-sm font-medium text-indigo-600">최근 기록</p>
          <h3 className="text-xl font-semibold text-slate-900">
            방금 플레이한 사용자
          </h3>
        </div>
      </header>

      <div className="mt-4 space-y-3">
        {data.length === 0 ? (
          <p className="text-sm text-slate-500">
            아직 오늘의 플레이 기록이 없습니다. 첫 번째 기록을 만들어 보세요!
          </p>
        ) : (
          data.map((item, index) => (
            <article
              key={`${item.nickname}-${index}`}
              className="flex items-center justify-between rounded-2xl border border-slate-100 px-4 py-3"
            >
              <div>
                <p className="text-base font-medium text-slate-900">
                  {item.nickname}
                </p>
                <p className="text-xs text-slate-400">
                  {item.modifiedAt
                    ? formatter.format(new Date(item.modifiedAt))
                    : "방금 전"}
                </p>
              </div>
              <p className="text-lg font-semibold text-slate-900">
                {item.value}
              </p>
            </article>
          ))
        )}
      </div>
    </section>
  );
}
