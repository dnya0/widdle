package toyproject.widdle.widdle.word.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import toyproject.widdle.widdle.word.controller.dto.WordResponse
import toyproject.widdle.widdle.word.controller.dto.WordSaveRequest
import toyproject.widdle.widdle.word.service.WordService
import toyproject.widdle.widdle.support.toResponse

@RestController
@RequestMapping("/api")
class WordController(
    private val wordService: WordService
) {

    @GetMapping("/{language}")
    fun getWord(@PathVariable language: String): ResponseEntity<WordResponse> =
        wordService.getDailyWord(isKorean(language)).toResponse()

    @GetMapping
    fun hasWord(@RequestParam q: List<String>): ResponseEntity<Boolean> =
        wordService.hasWord(q).toResponse()

    @PostMapping
    fun saveWord(@RequestBody request: WordSaveRequest): ResponseEntity<String> =
        wordService.save(request).toResponse()

    @PatchMapping("/{id}")
    fun updateWordToUse(@PathVariable id: String) = wordService.use(id)

    private fun isKorean(language: String): Boolean = language == "kr"

}