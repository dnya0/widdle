package day.widdle.widdle.global.transaction

import day.widdle.widdle.global.transaction.helper.Tx
import day.widdle.widdle.global.transaction.runner.TransactionRunner
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.support.TransactionSynchronization
import org.springframework.transaction.support.TransactionSynchronizationManager
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [TxIntegrationTest.TxTestConfig::class])
class TxIntegrationTest {

    @Test
    fun writable_starts_transaction_and_not_readOnly() {
        var active = false
        var readOnly = true

        val result = Tx.writable {
            active = TransactionSynchronizationManager.isActualTransactionActive()
            readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            42
        }

        assertEquals(42, result)
        assertTrue(active, "writable should start a transaction")
        assertFalse(readOnly, "writable transaction should not be read-only")
    }

    @Test
    fun readable_with_REQUIRED_starts_transaction_and_is_readOnly() {
        var active = false
        var readOnly = false
        Tx.readable {
            active = TransactionSynchronizationManager.isActualTransactionActive()
            readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly()
        }
        assertTrue(active, "readable with REQUIRED should start a transaction when none exists")
        assertTrue(readOnly, "readable transaction should be read-only")
    }

    @Test
    fun readable_inside_writable_joins_existing_tx_and_is_not_readOnly() {
        var innerActive = false
        var innerReadOnly = true

        Tx.writable {
            Tx.readable {
                innerActive = TransactionSynchronizationManager.isActualTransactionActive()
                innerReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            }
        }

        assertTrue(innerActive, "readable should join existing transaction (SUPPORTS)")
        assertFalse(innerReadOnly, "joined transaction should keep writable semantics (not read-only)")
    }

    @Test
    fun writable_commits_on_success_and_rolls_back_on_runtime_exception() {
        var committedStatus: Int? = null
        var rolledBackStatus: Int? = null

        // commit path
        Tx.writable {
            TransactionSynchronizationManager.registerSynchronization(object : TransactionSynchronization {
                override fun afterCompletion(status: Int) {
                    committedStatus = status
                }
            })
        }
        assertEquals(TransactionSynchronization.STATUS_COMMITTED, committedStatus)

        // rollback path
        assertThrows(RuntimeException::class.java) {
            Tx.writable {
                TransactionSynchronizationManager.registerSynchronization(object : TransactionSynchronization {
                    override fun afterCompletion(status: Int) {
                        rolledBackStatus = status
                    }
                })
                throw RuntimeException("boom")
            }
        }
        assertEquals(TransactionSynchronization.STATUS_ROLLED_BACK, rolledBackStatus)
    }

    @TestConfiguration
    @EnableTransactionManagement
    class TxTestConfig {
        @Bean
        fun dataSource(): DataSource = DriverManagerDataSource().apply {
            setDriverClassName("org.h2.Driver")
            url = "jdbc:h2:mem:widdle-tx;DB_CLOSE_DELAY=-1;MODE=PostgreSQL"
            username = "sa"
            password = ""
        }

        @Bean
        fun transactionManager(ds: DataSource): PlatformTransactionManager = DataSourceTransactionManager(ds)

        @Bean
        fun transactionRunner(): TransactionRunner = TransactionRunner()

        // Initialize Tx with the TransactionRunner bean
        @Bean
        fun txInitializer(runner: TransactionRunner): InitializingBean = InitializingBean {
            Tx.initialize(runner)
        }
    }
}
