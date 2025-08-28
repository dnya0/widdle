package toyproject.widdle.widdle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class WiddleApplication

fun main(args: Array<String>) {
    runApplication<WiddleApplication>(*args)
}
