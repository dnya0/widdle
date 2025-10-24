package day.widdle.widdle.correction.service.dto.bareun

data class CorrectErrorResponse(
    val origin: String,
    val revised: String,
    val revisedBlocks: List<RevisedBlock> = emptyList(),
    val whitespaceCleanupRanges: List<CleanUpRange> = emptyList(),
    val revisedSentences: List<RevisedSentence> = emptyList(),
    val helps: Map<String, ReviseHelp> = emptyMap(),
    val language: String,
    val tokensCount: Int
)

data class RevisedSentence(
    val origin: String,
    val revised: String
)

data class RevisedBlock(
    val origin: String,
    val revised: String,
    val revisions: List<Revision> = emptyList(),
    val nested: List<RevisedBlock> = emptyList(),
    val lemma: String? = null,
    val pos: CustomDictPos? = null
)

data class Revision(
    val revised: String,
    val score: Double,
    val category: RevisionCategory,
    val helpId: String
)

data class ReviseHelp(
    val id: String,
    val category: RevisionCategory,
    val comment: String,
    val examples: List<String> = emptyList(),
    val ruleArticle: String
)

data class CleanUpRange(
    val offset: Int,
    val length: Int,
    val position: CleanUpRangePosition
)

// 사용자 사전용 형태소
enum class CustomDictPos {
    POS_UNK,     // 해당 없음
    POS_NNG,     // 복합명사 사전
    POS_NNP,     // 고유명사 사전
    POS_NNG_CARET, // 복합명사 분리 사전
    POS_VV,      // 동사 사전
    POS_VA,      // 형용사 사전
    POS_MM,      // 관형사 사전
    POS_IC       // 감탄사 사전
}

// 한국어 문장 교정의 카테고리
enum class RevisionCategory {
    UNKNOWN,          // 교정 카테고리가 없음
    GRAMMER,          // 어법에 관한 사항
    WORD,             // 어법 단어 단위 규칙에 관한 사항
    SPACING,          // 띄어쓰기에 관한 사항
    STANDARD,         // 표준어 일반
    TYPO,             // 오탈자
    FOREIGN_WORD,     // 외래어 표기법
    CONFUSABLE_WORDS, // 혼동하기 쉬운 단어
    SENTENCE,         // 문장 단위 오류
    CONFIRM           // 확인 필요
}

// CleanUpRange 지워야할 위치에 대한 추가 정보
enum class CleanUpRangePosition {
    START,  // 처음
    END,    // 끝
    MIDDLE  // 중간
}