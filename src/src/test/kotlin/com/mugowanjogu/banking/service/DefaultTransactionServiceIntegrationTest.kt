package com.mugowanjogu.banking.service

import com.mugowanjogu.banking.AppConfig
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig(classes = [AppConfig::class])
internal class DefaultTransactionServiceIntegrationTest {

    @Autowired
    lateinit var transactionService: TransactionService

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
    }

    @Test
    fun transactionList() {
        val list = transactionService.transactionList("savings-kids-john")
        assertEquals(3, list.size, "tx count")
    }

    @org.junit.jupiter.api.Test
    fun transactionsByType() {
    }

    @org.junit.jupiter.api.Test
    fun totalForTransactionsType() {
    }
}