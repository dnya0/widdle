package toyproject.widdle.widdle.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import toyproject.widdle.widdle.controller.dto.WordRequest
import toyproject.widdle.widdle.controller.dto.WordResponse
import toyproject.widdle.widdle.controller.dto.WordSaveRequest
import toyproject.widdle.widdle.service.WordService
import toyproject.widdle.widdle.support.toResponse

@RestController
class WordController(
    private val wordService: WordService
) {

    @GetMapping
    fun getWord(): ResponseEntity<WordResponse> = wordService.getDailyWord().toResponse()

    @GetMapping()
    fun hasWord(@RequestBody request: WordRequest): ResponseEntity<Boolean> =
        wordService.hasWord(request).toResponse()

    @PostMapping
    fun saveWord(@RequestBody request: WordSaveRequest): ResponseEntity<String> =
        wordService.save(request).toResponse()

}