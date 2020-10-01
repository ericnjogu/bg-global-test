package com.mugowanjogu.banking.service

import com.mugowanjogu.banking.AppConfig
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.math.BigDecimal

@SpringJUnitConfig(classes = [AppConfig::class])
internal class DefaultTransactionServiceIntegrationTest {

    companion object {
        val log: Log = LogFactory.getLog(DefaultTransactionServiceIntegrationTest::class.java)
    }

    @Autowired
    lateinit var transactionService: TransactionService

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
    }

    @Test
    fun `transactionList() should return transactions for known account`() {
        val list = transactionService.transactionList("savings-kids-john")
        assertEquals(3, list.size, "tx count")
    }

    @Test
    fun `transactionList() should empty list for unknown account`() {
        val list = transactionService.transactionList("current-kids-john")
        assertTrue(list.isEmpty(), "tx list")
    }

    @org.junit.jupiter.api.Test
    fun `transactionsByType() should return all transactions that match type`() {
        val list = transactionService.transactionsByType("savings-kids-john", "SEPA")
        assertEquals(3, list.size, "tx count")
    }

    @org.junit.jupiter.api.Test
    fun `transactionsByType() should return no transactions when type does not match`() {
        val list = transactionService.transactionsByType("savings-kids-john", "HEPA")
        assertTrue(list.isEmpty(), "tx list")
    }

    @org.junit.jupiter.api.Test
    fun `totalForTransactionsType() should return correct total for known type`() {
        val total = transactionService.totalForTransactionsType("savings-kids-john", "SEPA")
        log.debug("account: savings-kids-john, type: SEPA -> total is $total")
        assertEquals("25.80", total.toString(), "tx type total")
    }

    @org.junit.jupiter.api.Test
    fun `totalForTransactionsType() should return zero for unknown type`() {
        val total = transactionService.totalForTransactionsType("savings-kids-john", "HEPA")
        log.debug("account: savings-kids-john, type: HEPA -> total is $total")
        assertEquals(0, total.compareTo(BigDecimal.ZERO), "tx type total")
    }

    @org.junit.jupiter.api.Test
    fun `totalForTransactionsType() should return zero for unknown account id`() {
        val total = transactionService.totalForTransactionsType("loan-kids-john", "SEPA")
        assertEquals(0, total.compareTo(BigDecimal.ZERO), "tx type total")
    }
}