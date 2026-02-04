package day.widdle.widdle.global.transaction.config

import day.widdle.widdle.global.transaction.helper.Tx
import day.widdle.widdle.global.transaction.runner.TransactionRunner
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TransactionConfig {

    @Bean("txInitBean")
    fun txInitialize(txRunner: TransactionRunner): InitializingBean =
        InitializingBean { Tx.initialize(txRunner) }
}
