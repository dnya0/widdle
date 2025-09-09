export type GuessRecord = {
  timestamp: number;
  guess: [number, string[]][];
  lang: "kr" | "en";
};

export type StatsRacord = {
  bestStreak: number;
  currentStreak: number;
  totalStreak: number;
  successRate: number;
  lang: "kr" | "en";
  timestamp: number;
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

export function makeStateAndSave(
  lang: "kr" | "en",
  colors: number[],
  cur: {
    row: number;
    col: number;
  },
  ROWS: number
) {
  const winDistribution = [0, 0, 0, 0, 0, 0];

  if (cur.row === ROWS - 1 && !colors.every((c) => c === 3)) {
    saveStats({
      bestStreak: 0,
      currentStreak: 0,
      totalStreak: 1,
      successRate: 0,
      lang: lang,
      timestamp: Date.now(),
      winDistribution: winDistribution,
    });
    return;
  }
  winDistribution[cur.row] = 1;

  saveStats({
    bestStreak: 1,
    currentStreak: 1,
    totalStreak: 1,
    successRate: 100,
    lang: lang,
    timestamp: Date.now(),
    winDistribution: winDistribution,
  });
}

export function saveGuess(lang: "kr" | "en", guess: string[]) {
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
  if (prev?.timestamp && isSameDay(prev.timestamp, Date.now())) {
    return;
  }
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

  const totalStreak = prev.totalStreak + 1;

  const totalWins = winDistribution.reduce((sum, val) => sum + val, 0);
  const successRate =
    totalStreak === 0
      ? 0
      : Number(((totalWins / totalStreak) * 100).toFixed(1));

  return {
    bestStreak: bestStreak,
    currentStreak: currentStreak,
    totalStreak: totalStreak,
    successRate: successRate,
    timestamp: Date.now(),
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
