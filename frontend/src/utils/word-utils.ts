// src/utils/word-utils.ts
export type Cell = { text: string; colorIndex?: number };

export const ROWS = 6;
export const COLS = 6;

export const colors = [
  "#dfdfdfff",
  "#a4a4a4ff",
  "rgba(238, 190, 0, 1)",
  "rgba(17, 198, 0, 1)",
];

/**
 * 사용자가 입력한 guess와 정답 answer를 비교해서 색상 인덱스 리턴
 * colorIndex:
 *  - 0: 단어에 있지만 위치 다름
 *  - 1: 단어에 있고 위치도 맞음
 *  - 2: 단어에 없음
 */
export function evaluateGuess(guess: string[], answer: string[]): number[] {
  const n = answer.length;
  const res = Array(n).fill(1); // 일단 전부 '없음(회색)'으로 시작
  const count = new Map<string, number>();

  // 1) 정답 글자 개수 집계
  for (const ch of answer) count.set(ch, (count.get(ch) ?? 0) + 1);

  // 2) 초록(정위치) 먼저 처리 → 개수 차감
  for (let i = 0; i < n; i++) {
    if (guess[i] === answer[i]) {
      res[i] = 3; // 초록
      count.set(guess[i], (count.get(guess[i]) ?? 0) - 1);
    }
  }

  // 3) 노랑(존재) 처리: 남은 개수 있을 때만
  for (let i = 0; i < n; i++) {
    if (res[i] === 3) continue; // 이미 초록 처리
    const g = guess[i];
    if ((count.get(g) ?? 0) > 0) {
      res[i] = 2; // 노랑
      count.set(g, (count.get(g) ?? 0) - 1);
    } else {
      res[i] = 1; // 회색
    }
  }

  return res;
}
