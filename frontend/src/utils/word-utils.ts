import { isConsonant, isVowel } from "hangul-js";

export type Cell = { text: string; colorIndex?: number };

export const ROWS = 6;
export const COLS = 6;

export const colors = [
  "#dfdfdfff",
  "#a4a4a4ff",
  "rgba(238, 190, 0, 1)",
  "rgba(17, 198, 0, 1)",
];

export const colorRed = "#ff6868ff";

export interface ModalProps {
  open: boolean;
  lang: "kr" | "en";
  onClose: () => void;
}

export const mergeKeyColor = (oldC: number | undefined, newC: number) => {
  if (newC === 3) return 3;
  if (newC === 2) return oldC === 3 ? 3 : 2;
  if (newC === 1) return oldC && oldC > 1 ? oldC : 1;
  return oldC ?? 0;
};

export function initBoard(rows = 6, cols = 6): Cell[][] {
  return Array.from({ length: rows }, () =>
    Array.from({ length: cols }, () => ({ text: "" }))
  );
}

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

export function getSecondsUntilMidnight(): number {
  const now = new Date();
  const midnight = new Date();
  midnight.setHours(24, 0, 0, 0);

  const diffMs = midnight.getTime() - now.getTime();
  return Math.max(0, Math.floor(diffMs / 1000));
}

export function formatSecondsToHHMMSS(seconds: number): string {
  const h = Math.floor(seconds / 3600).toString().padStart(2, "0");
  const m = Math.floor((seconds % 3600) / 60).toString().padStart(2, "0");
  const s = (seconds % 60).toString().padStart(2, "0");
  return `${h}:${m}:${s}`;
}

export function combineVowels(currentVowel: string, nextVowel: string): string {
    // 실제 구현에서는 'ㅗ' + 'ㅏ' -> 'ㅘ', 'ㅜ' + 'ㅓ' -> 'ㅝ' 등의 로직이 들어갑니다.
    const combinationMap: { [key: string]: string } = {
        'ㅗㅏ': 'ㅘ', 'ㅗㅐ': 'ㅙ', 'ㅗㅣ': 'ㅚ',
        'ㅜㅓ': 'ㅝ', 'ㅜㅔ': 'ㅞ', 'ㅜㅣ': 'ㅟ',
        'ㅡㅣ': 'ㅢ', 'ㅏㅣ': 'ㅐ', 'ㅓㅣ': 'ㅔ',
        'ㅕㅣ': 'ㅖ', "ㅑㅣ": "ㅒ"
        // 여기에 모든 복모음 조합을 추가합니다.
    };
    return combinationMap[currentVowel + nextVowel] || currentVowel; 
}

// 자모 배열을 순회하며 모음 연속을 처리하는 함수
export function assembleAndCombineVowels(words: string[]): string[] {
    const combinedWords: string[] = [];
    
    for (let i = 0; i < words.length; i++) {
        const current = words[i];
        const next = words[i + 1];

        // es-hangul의 isVowel을 사용하여 모음인지 확인
        if (isVowel(current) && isVowel(next)) {
            // 모음이 2번 연속으로 나오는 경우
            const combined = combineVowels(current, next);
            
            // 유효하게 복모음으로 결합된 경우
            if (combined.length === 1 && combined !== current) { 
                combinedWords.push(combined);
                i++; // 다음 모음은 이미 사용했으므로 건너뛰기
            } else {
                // 결합되지 않는 모음 조합은 그대로 추가 (예: ㅏ + ㅓ)
                combinedWords.push(current);
            }
        } else {
            // 자음이거나 마지막 자모는 그대로 추가
            combinedWords.push(current);
        }
    }
    
    return combinedWords;
}

export function combineConsonants(currentConsonant: string, nextConsonant: string): string {
    // 쌍자음 조합 맵
    const combinationMap: { [key: string]: string } = {
        'ㄱㄱ': 'ㄲ',
        'ㄷㄷ': 'ㄸ',
        'ㅂㅂ': 'ㅃ',
        'ㅅㅅ': 'ㅆ',
        'ㅈㅈ': 'ㅉ',
    };

    const key = currentConsonant + nextConsonant;
    
    return combinationMap[key] || currentConsonant; 
}

export function assembleAndCombineConsonants(words: string[]): string[] {
    const current = words[0];
    const next = words[1];

    if (isConsonant(current) && isConsonant(next)) {
        const combined = combineConsonants(current, next);
        
        if (combined.length === 1 && combined !== current) { 
            return [combined, ...words.slice(2)];
        } 
    }
    
    return words;
}

// export function assembleAndCombineConsonants(words: string[]): string[] {
//     const combinedWords: string[] = [];

//     if (words[0] = words[1] ) {

//     }
    
//     for (let i = 0; i < words.length; i++) {
//         const current = words[i];
//         const next = words[i + 1];

//         // isConsonant 함수를 사용하여 자음인지 확인
//         if (isConsonant(current) && isConsonant(next)) {
//             // 자음이 2번 연속으로 나오는 경우
//             const combined = combineConsonants(current, next);
            
//             // 유효하게 쌍자음으로 결합된 경우 (결합 후 길이가 1이고, 결합 전과 다를 때)
//             if (combined.length === 1 && combined !== current) { 
//                 combinedWords.push(combined);
//                 i++; // 다음 자음은 이미 사용했으므로 건너뛰기
//             } else {
//                 // 결합되지 않는 자음 조합은 그대로 추가 (예: ㄱ + ㄴ)
//                 combinedWords.push(current);
//             }
//         } else {
//             // 모음이거나 마지막 자모는 그대로 추가
//             combinedWords.push(current);
//         }
//     }
    
//     return combinedWords;
// }

export function checkRepetitiveJamo(words: string[]): boolean {
    const firstJamo = words[0];
    return words.every(jamo => jamo === firstJamo);
}

export function checkCAndVBalance(words: string[]): boolean {
    let hasConsonant = false;
    let hasVowel = false;
    
    for (const jamo of words) {
        if (isConsonant(jamo)) {
            hasConsonant = true;
        }
        if (isVowel(jamo)) {
            hasVowel = true;
        }
    }
    // 자음과 모음이 모두 존재해야 true
    return hasConsonant && hasVowel;
}