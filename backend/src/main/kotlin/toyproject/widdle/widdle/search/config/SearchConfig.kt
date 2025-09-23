package toyproject.widdle.widdle.search.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import toyproject.widdle.widdle.search.service.api.NaverSearchApi
import toyproject.widdle.widdle.search.service.SearchApi
import toyproject.widdle.widdle.search.service.api.EnglishSearchApi
import toyproject.widdle.widdle.search.value.DictionaryType

@Configuration
class SearchConfig {

    @Bean
    fun searchApis(
        naverSearchApi: NaverSearchApi,
        englishSearchApi: EnglishSearchApi
    ): Map<DictionaryType, SearchApi> = mapOf(
        DictionaryType.NAVER to naverSearchApi,
        DictionaryType.ENGLISH to englishSearchApi
    )

    @Bean
    fun naverSearchApi(clientProperties: ClientProperties): NaverSearchApi = NaverSearchApi(clientProperties)

    @Bean
    fun englishSearchApi(clientProperties: ClientProperties): EnglishSearchApi = EnglishSearchApi(clientProperties)

}