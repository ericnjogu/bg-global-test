package com.mugowanjogu.banking.service

import com.mugowanjogu.banking.dto.OpbTransaction
import java.math.BigDecimal

interface TransactionService {
    fun transactionList(accountId: String): List<OpbTransaction>

    fun transactionsByType(accountId: String, type: String): List<OpbTransaction>

    fun totalForTransactionsType(accountId: String, type: String): BigDecimal
}