package toyproject.widdle.widdle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WiddleApplication

fun main(args: Array<String>) {
    runApplication<WiddleApplication>(*args)
}
