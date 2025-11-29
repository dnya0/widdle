package day.widdle.widdle.search.service.dto

import com.fasterxml.jackson.annotation.JsonIgnore

/**
 * 국립국어원 한국어기초사전 api
 */
data class KbasResponse(
    val header: HeaderDto,
    val body: BodyDto,
)

data class BodyDto(
    val items: ItemsDto,
    val numOfRows: String,
    val pageNo: String,
    val totalCount: String
)

data class ItemsDto(
    val item: List<KbasItemDto>
)

data class HeaderDto(
    val resultCode: String,
    val resultMsg: String,

    @get:JsonIgnore
    val codeEnum: ErrorCode = ErrorCode.of(resultCode),
)

data class KbasItemDto(
    val title: String,
    val alternativeTitle: String,
    val issuedDate: String,
    val description: String,
    val format: String,
    val language: String,
    val uci: String,
    val url: String,
    val collectedDate: String,
    val regDt: String
)

enum class ErrorCode(val code: String, val message: String) {
    OK("0000", "정상 처리"),
    NOT_FOUND("F2013", "서비스 주소 호출 실패"),
    SERVER_ERROR("9999", "서비스 점검중 (내부 서비스 호출 장애)"),
    UNKNOWN("알 수 없는 코드", "알 수 없는 코드");

    companion object {
        fun of(code: String): ErrorCode = entries.find { it.code == code } ?: UNKNOWN
    }
}
