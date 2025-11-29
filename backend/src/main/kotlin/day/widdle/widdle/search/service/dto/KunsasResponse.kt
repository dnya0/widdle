package day.widdle.widdle.search.service.dto

import kotlinx.serialization.Serializable

/**
 * 표준국어대사전 api
 */
@Serializable
data class KunsasResponse(val channel: ChannelKunsas)

@Serializable
data class ChannelKunsas(
    val title: String,
    val link: String,
    val description: String,
    @kotlinx.serialization.SerialName("lastbuilddate")
    val lastBuildDate: String?,
    val total: Int,
    val start: Int,
    val num: Int,
    val item: List<KunsasItemDto> // 검색 결과 단어 목록
)

@Serializable
data class KunsasItemDto(
    @kotlinx.serialization.SerialName("target_code")
    val targetCode: Int,
    val word: String,
    @kotlinx.serialization.SerialName("sup_no")
    val supNo: Int,
    val pos: String,
    val sense: SenseDto
)

@Serializable
data class SenseDto(
    val definition: String,
    val link: String,
    val type: String
)
