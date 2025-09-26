package day.widdle.widdle.search.service.dto

data class KoreanResponse(
    val description: String?,
    val item: List<ItemKr>?,
    val lastBuildDate: Long?,
    val link: String,
    val num: Int,
    val start: Int,
    val title: String?,
    val total: Int
)

data class ItemKr(
    val link: String,
    val pos: String,
    val sense: List<SenseKr>,
    val sup_no: Int?,
    val target_code: Int?,
    val word: String?,
)

data class SenseKr(
    val definition: String,
    val sense_order: Int
)