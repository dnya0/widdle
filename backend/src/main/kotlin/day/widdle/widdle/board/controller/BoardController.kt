package day.widdle.widdle.board.controller

import day.widdle.widdle.board.controller.dto.StatisticsListResponse
import day.widdle.widdle.board.controller.dto.StatisticsUpsertRequest
import day.widdle.widdle.board.service.BoardService
import day.widdle.widdle.board.service.dto.toStatisticsListResponse
import day.widdle.widdle.global.base.ResponseData
import day.widdle.widdle.global.support.isKoreanCode
import day.widdle.widdle.global.support.toResponse
import org.springframework.web.bind.annotation.GetMapping
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
    fun saveStatistics(@RequestBody request: StatisticsUpsertRequest) = boardService.upsert(request.toDto())

}
