export type Status = "correct" | "present" | "absent";

export type GuessRecord = {
  timestamp: number;
  guess: string[];
  statuses: Status[];
  row: number;
  lang: "ko" | "en";
};

const isBrowser = () => typeof window !== "undefined";

function makeKey(gameId: string, dateKey?: string) {
  return dateKey ? `widdle:${gameId}:${dateKey}` : `widdle:${gameId}`;
}

export function loadHistory(gameId: string, dateKey?: string): GuessRecord[] {
  if (!isBrowser()) return [];
  try {
    const raw = localStorage.getItem(makeKey(gameId, dateKey));
    return raw ? (JSON.parse(raw) as GuessRecord[]) : [];
  } catch {
    return [];
  }
}

export function saveGuess(
  gameId: string,
  record: GuessRecord,
  dateKey?: string,
  maxItems = 10
) {
  if (!isBrowser()) return;
  const key = makeKey(gameId, dateKey);
  const prev = loadHistory(gameId, dateKey);
  const next = [...prev, record].slice(-maxItems); // 최신 maxItems개만 유지
  localStorage.setItem(key, JSON.stringify(next));
}

export function clearHistory(gameId: string, dateKey?: string) {
  if (!isBrowser()) return;
  localStorage.removeItem(makeKey(gameId, dateKey));
}