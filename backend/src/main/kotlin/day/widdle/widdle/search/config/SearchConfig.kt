package day.widdle.widdle.search.config

import day.widdle.widdle.search.service.SearchApi
import day.widdle.widdle.search.service.api.EnglishSearchApi
import day.widdle.widdle.search.service.api.KoreanSearchApi
import day.widdle.widdle.search.service.api.NaverSearchApi
import day.widdle.widdle.search.value.DictionaryType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SearchConfig {

    @Bean
    fun searchApis(
        naverSearchApi: NaverSearchApi,
        englishSearchApi: EnglishSearchApi,
        koreanSearchApi: KoreanSearchApi,
    ): Map<DictionaryType, SearchApi> = mapOf(
        DictionaryType.NAVER to naverSearchApi,
        DictionaryType.ENGLISH to englishSearchApi,
        DictionaryType.KOREAN to koreanSearchApi
    )
}