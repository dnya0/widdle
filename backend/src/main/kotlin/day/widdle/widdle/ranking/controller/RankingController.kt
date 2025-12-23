package day.widdle.widdle.ranking.controller

import day.widdle.widdle.global.base.ResponseData
import day.widdle.widdle.global.support.isKoreanCode
import day.widdle.widdle.global.support.toResponse
import day.widdle.widdle.ranking.controller.dto.StatisticsListResponse
import day.widdle.widdle.ranking.controller.dto.request.StatisticsSaveRequest
import day.widdle.widdle.ranking.controller.dto.request.StatisticsUpdateRequest
import day.widdle.widdle.ranking.service.RankingService
import day.widdle.widdle.ranking.service.dto.toStatisticsListResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ranking")
class RankingController(
    private val rankingService: RankingService
) {

    @GetMapping("/{language}/{id}")
    fun getRanking(
        @PathVariable language: String, @PathVariable("id") id: String
    ): ResponseData<StatisticsListResponse> = rankingService.getRankingStatistics(id, language.isKoreanCode())
        .toStatisticsListResponse()
        .toResponse()

    @PostMapping
    fun saveStatistics(@RequestBody request: StatisticsSaveRequest): ResponseData<String> =
        rankingService.save(request.toDto()).toResponse()

    @PatchMapping("/{id}")
    fun updateStatistics(
        @PathVariable("id") id: String,
        @RequestBody request: StatisticsUpdateRequest
    ): ResponseData<String> = rankingService.update(request.toDto(id)).toResponse(HttpStatus.CREATED)


}
