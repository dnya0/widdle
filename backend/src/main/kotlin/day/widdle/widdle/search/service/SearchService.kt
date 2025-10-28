package day.widdle.widdle.search.service

import day.widdle.widdle.event.NewWordEvent
import day.widdle.widdle.event.publisher.WiddleEventPublisher
import day.widdle.widdle.log.logger
import day.widdle.widdle.search.value.DictionaryType
import day.widdle.widdle.search.value.getDictionaryType
import org.springframework.stereotype.Service

@Service
class SearchService(
    private val publisher: WiddleEventPublisher,
    private val searchApis: Map<DictionaryType, SearchApi>
) {
    private val log = logger()

    suspend fun hasWordInDictionary(word: String, wordJamo: List<String>): Boolean {
        val api = searchApis[word.getDictionaryType()]
        val flag = api?.search(word) ?: false

        publisher.publishEvent(flag, NewWordEvent.to(word, wordJamo))

        return flag
    }

}
