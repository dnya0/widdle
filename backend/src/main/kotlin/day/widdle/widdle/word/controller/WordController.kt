package day.widdle.widdle.word.controller

import day.widdle.widdle.global.base.ResponseData
import day.widdle.widdle.global.support.isKoreanCode
import day.widdle.widdle.global.support.toResponse
import day.widdle.widdle.global.support.toUpperCaseIfEnglish
import day.widdle.widdle.word.controller.dto.WordResponse
import day.widdle.widdle.word.controller.dto.WordSaveRequest
import day.widdle.widdle.word.service.WordService
import day.widdle.widdle.word.service.dto.toWordResponse
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class WordController(
    private val wordService: WordService
) {

    @GetMapping("/{language}")
    fun getWord(@PathVariable language: String): ResponseData<WordResponse> =
        wordService.getDailyWord(language.isKoreanCode()).toWordResponse().toResponse()

    @GetMapping
    suspend fun hasWord(@RequestParam word: String, @RequestParam q: List<String>): ResponseData<Boolean> =
        wordService.hasWord(word.toUpperCaseIfEnglish(), q).toResponse()

    @PostMapping
    fun saveWord(@RequestBody request: WordSaveRequest): ResponseData<String> =
        wordService.createWordFromDtoTx(request.toDto()).toResponse(CREATED)

    @PatchMapping("/{id}")
    fun updateWordToUse(@PathVariable id: String) = wordService.use(id)

}
