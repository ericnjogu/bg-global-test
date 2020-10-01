package com.mugowanjogu.banking.service

import com.mugowanjogu.banking.dto.BackbaseTransaction
import java.math.BigDecimal

interface BackbaseTransactionService {
    fun transactionList(accountId: String): List<BackbaseTransaction>

    fun transactionsByType(accountId: String, type: String): List<BackbaseTransaction>

    fun totalForTransactionsType(accountId: String, type: String): BigDecimal
}