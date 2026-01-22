package day.widdle.widdle.global.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import day.widdle.widdle.word.service.dto.DailyWordDto
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
class RedisCacheConfig {

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper()
        .registerModule(kotlinModule())
        .registerModule(JavaTimeModule())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    @Bean
    fun cacheManager(
        connectionFactory: RedisConnectionFactory,
        objectMapper: ObjectMapper
    ): RedisCacheManager {
        val keySer = StringRedisSerializer()
        val valueSer = GenericJackson2JsonRedisSerializer(objectMapper)

        val wordSer = Jackson2JsonRedisSerializer(objectMapper, DailyWordDto::class.java)
        val boolSer = Jackson2JsonRedisSerializer(objectMapper, Boolean::class.java)

        fun configWith(valSer: RedisSerializer<*>, ttl: Duration) =
            RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(fromSerializer(keySer))
                .serializeValuesWith(fromSerializer(valSer))
                .entryTtl(ttl)
                .disableCachingNullValues()

        val defaultConfig = configWith(valueSer, Duration.ofDays(1))
        val dailyWordConfig = configWith(wordSer, Duration.ofDays(1))
        val hasWordConfig = configWith(boolSer, Duration.ofDays(10))
        val rankingConfig = configWith(valueSer, Duration.ofDays(1))

        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(defaultConfig)
            .withCacheConfiguration("dailyWordCache", dailyWordConfig)
            .withCacheConfiguration("hasWord", hasWordConfig)
            .withCacheConfiguration("topRankings", rankingConfig)
            .build()
    }
}
