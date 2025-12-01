package day.widdle.widdle.search.service.dto

data class DictionaryEntry(
    val meta: Meta,
    val hom: Int? = null, // 동음이의어 번호 (Homograph Number)
    val hwi: HeadwordInfo,
    val fl: String, // 품사 (Function Label, noun, verb, etc.)
    val def: List<DefinitionEntry>, // 정의 배열
    val et: List<List<String>>? = null, // 어원 (Etymology)
    val date: String? = null, // 최초 사용 연도
    val shortdef: List<String> // 간략 정의
)

data class Meta(
    val id: String, // test:1, test:2, test ban 등 고유 ID
    val uuid: String,
    val src: String, // 출처 (collegiate)
    val stems: List<String>, // 어간/변형된 단어
    val offensive: Boolean
)

data class HeadwordInfo(
    val hw: String, // 표제어 (Headword)
    val prs: List<Pronunciation>? = null // 발음 정보
)

data class Pronunciation(
    val mw: String, // 발음 기호 (e.g., ˈtest)
)

data class DefinitionEntry(
    val vd: String? = null,
    val sseq: Any? = null
)
