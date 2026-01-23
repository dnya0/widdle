type SummaryCard = {
  label: string;
  value: string;
  helperText?: string;
};

type SummaryCardsProps = {
  cards: SummaryCard[];
};

export function SummaryCards({ cards }: SummaryCardsProps) {
  if (!cards.length) return null;

  return (
    <section className="grid w-full gap-4 sm:grid-cols-2">
      {cards.map((card) => (
        <article
          key={card.label}
          className="rounded-2xl border border-slate-200 bg-white/80 p-5 shadow-sm backdrop-blur"
        >
          <p className="text-sm text-slate-500">{card.label}</p>
          <p className="mt-2 text-3xl font-semibold text-slate-900">
            {card.value}
          </p>
          {card.helperText ? (
            <p className="mt-1 text-xs text-slate-400">{card.helperText}</p>
          ) : null}
        </article>
      ))}
    </section>
  );
}
