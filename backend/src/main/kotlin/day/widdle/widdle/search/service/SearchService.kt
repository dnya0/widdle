package day.widdle.widdle.search.service

import day.widdle.widdle.logger.logger
import day.widdle.widdle.search.event.NewWordEvent
import day.widdle.widdle.search.value.DictionaryType
import day.widdle.widdle.search.value.getDictionaryType
import day.widdle.widdle.support.isKorean
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class SearchService(
    private val publisher: ApplicationEventPublisher,
    private val searchApis: Map<DictionaryType, SearchApi>
) {
    private val log = logger()

    suspend fun hasWordInDictionary(word: String, wordJamo: List<String>): Boolean {
        val api = searchApis[word.getDictionaryType()]
        val flag = api?.search(word) ?: false

        if (flag) {
            publisher.publishEvent(NewWordEvent(word, wordJamo, word.isKorean()))
        }
        return flag
    }

}