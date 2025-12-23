package day.widdle.widdle.board.controller

import day.widdle.widdle.board.controller.dto.StatisticsListResponse
import day.widdle.widdle.board.controller.dto.request.StatisticsSaveRequest
import day.widdle.widdle.board.controller.dto.request.StatisticsUpdateRequest
import day.widdle.widdle.board.service.BoardService
import day.widdle.widdle.board.service.dto.toStatisticsListResponse
import day.widdle.widdle.global.base.ResponseData
import day.widdle.widdle.global.support.isKoreanCode
import day.widdle.widdle.global.support.toResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/board")
class BoardController(
    private val boardService: BoardService
) {

    @GetMapping("/{language}/{id}")
    fun getBoard(
        @PathVariable language: String, @PathVariable("id") id: String
    ): ResponseData<StatisticsListResponse> = boardService.getRankingStatistics(id, language.isKoreanCode())
        .toStatisticsListResponse()
        .toResponse()

    @PostMapping
    fun saveStatistics(@RequestBody request: StatisticsSaveRequest): ResponseData<String> =
        boardService.save(request.toDto()).toResponse()

    @PatchMapping("/{id}")
    fun updateStatistics(
        @PathVariable("id") id: String,
        @RequestBody request: StatisticsUpdateRequest
    ): ResponseData<String> = boardService.update(request.toDto(id)).toResponse()


}
