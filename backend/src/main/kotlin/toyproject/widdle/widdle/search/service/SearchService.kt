package toyproject.widdle.widdle.search.service

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import toyproject.widdle.widdle.logger.logger
import toyproject.widdle.widdle.search.event.NewWordEvent
import toyproject.widdle.widdle.search.value.DictionaryType
import toyproject.widdle.widdle.support.isKorean

@Service
class SearchService(
    private val publisher: ApplicationEventPublisher,
    private val searchApis: Map<DictionaryType, SearchApi>
) {
    private val log = logger()

    fun hasWordInDictionary(word: String, wordJamo: List<String>): Boolean {
        val api = searchApis[DictionaryType.getType(word)]
        val flag = api?.search(word) ?: false

        if (flag) {
            publisher.publishEvent(NewWordEvent(word, wordJamo, word.isKorean()))
        }
        return flag
    }

}