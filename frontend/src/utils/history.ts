export type GuessRecord = {
  timestamp: number;
  guess: [number, string[]][];
  lang: "ko" | "en";
};

export type StatsRacord = {
  bestStreak: number;
  currentStreak: number;
  totalStreak: number;
  successRate: number;
  lang: "ko" | "en";
  winDistribution: number[];
};

const isBrowser = () => typeof window !== "undefined";

export function makeKey(gameId: string, key: string) {
  return `${key}:${gameId}`;
}

export function loadGuessRecord(key: string): GuessRecord | null {
  if (!isBrowser()) return null;

  try {
    const raw = localStorage.getItem(key);
    const prev = raw ? (JSON.parse(raw) as GuessRecord) : null;

    //guess 저장된 기록이 오늘 날짜가 아닐 경우 삭제
    if (prev !== null && !isSameDay(prev.timestamp, Date.now())) {
      localStorage.removeItem(key);
      return null;
    }
    return prev;
  } catch {
    return null;
  }
}

export function saveGuess(lang: "ko" | "en", guess: string[]) {
  if (!isBrowser()) return;
  const key = makeKey(lang, "gameState");
  const prev = loadGuessRecord(key);

  const next: GuessRecord = prev
    ? {
        timestamp: prev.timestamp,
        guess: [...prev.guess, [prev.guess.length, guess]],
        lang,
      }
    : {
        timestamp: Date.now(),
        guess: [[0, guess]],
        lang,
      };

  localStorage.setItem(key, JSON.stringify(next));
}

export function loadStatsRacord(key: string): StatsRacord | null {
  if (!isBrowser()) return null;
  try {
    const raw = localStorage.getItem(key);
    return raw ? (JSON.parse(raw) as StatsRacord) : null;
  } catch {
    return null;
  }
}

export function saveStats(record: StatsRacord) {
  if (!isBrowser()) return;
  const key = makeKey(record.lang, "gameStats");
  const prev = loadStatsRacord(key);
  const next = prev ? makeStats(prev, record) : record;

  localStorage.setItem(key, JSON.stringify(next));
}

function makeStats(prev: StatsRacord, record: StatsRacord): StatsRacord {
  if (!prev) {
    return record;
  }

  const currentStreak = record.currentStreak
    ? prev.currentStreak + record.currentStreak
    : 0;
  const bestStreak = currentStreak
    ? currentStreak >= prev.bestStreak
      ? currentStreak
      : prev.bestStreak
    : record.bestStreak;

  const winDistribution = [];
  for (let i = 0; i < prev.winDistribution.length; i++) {
    winDistribution[i] = prev.winDistribution[i] + record.winDistribution[i];
  }

  return {
    bestStreak: bestStreak,
    currentStreak: currentStreak,
    totalStreak: prev.totalStreak + 1,
    successRate: prev.successRate,
    lang: prev.lang,
    winDistribution: winDistribution,
  };
}

function isSameDay(ts1: number, ts2: number): boolean {
  const d1 = new Date(ts1);
  const d2 = new Date(ts2);

  return (
    d1.getFullYear() === d2.getFullYear() &&
    d1.getMonth() === d2.getMonth() &&
    d1.getDate() === d2.getDate()
  );
}

export function clearHistory(gameId: string, key: string) {
  if (!isBrowser()) return;
  localStorage.removeItem(makeKey(gameId, key));
}
