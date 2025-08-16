// src/utils/word-utils.ts
export type Cell = { text: string; colorIndex?: number };

export const ROWS = 6;
export const COLS = 6;

/**
 * 사용자가 입력한 guess와 정답 answer를 비교해서 색상 인덱스 리턴
 * colorIndex:
 *  - 0: 단어에 있지만 위치 다름
 *  - 1: 단어에 있고 위치도 맞음
 *  - 2: 단어에 없음
 */
export function evaluateGuess(guess: string[], answer: string[]): number[] {
  const result: number[] = Array(guess.length).fill(3); // 기본: 없음

  const answerCounts: Record<string, number> = {};
  for (const ch of answer) {
    answerCounts[ch] = (answerCounts[ch] ?? 0) + 1;
  }

  for (let i = 0; i < guess.length; i++) {
    if (guess[i] === answer[i]) {
      result[i] = 2;
      answerCounts[guess[i]]!--;
    }
  }

  for (let i = 0; i < guess.length; i++) {
    if (result[i] === 2) continue;
    const ch = guess[i];
    if (answerCounts[ch] && answerCounts[ch] > 0) {
      result[i] = 1;
      answerCounts[ch]!--;
    }
  }

  console.log(result);
  return result;
}
