package day.widdle.widdle.global.transaction.helper

import day.widdle.widdle.global.transaction.runner.TransactionRunner

object Tx {

    @Suppress("ktlint:standard:backing-property-naming")
    private lateinit var _txRunner: TransactionRunner

    fun initialize(txRunner: TransactionRunner) {
        _txRunner = txRunner
    }

    fun <T> writable(function: () -> T): T = _txRunner.run(function)

    fun <T> readable(function: () -> T): T = _txRunner.runReadOnly(function)

}
