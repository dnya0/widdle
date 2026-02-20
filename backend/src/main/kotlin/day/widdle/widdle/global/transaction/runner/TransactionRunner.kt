package day.widdle.widdle.global.transaction.runner

import day.widdle.widdle.global.transaction.annotation.ReadOnlyTransactional
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TransactionRunner {

    @Transactional
    fun <T> run(block: () -> T): T = block()

    @ReadOnlyTransactional
    fun <T> runReadOnly(block: () -> T): T = block()
}
