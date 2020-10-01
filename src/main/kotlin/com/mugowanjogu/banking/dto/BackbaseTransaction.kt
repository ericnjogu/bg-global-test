package com.mugowanjogu.banking.dto

import java.math.BigDecimal

data class BackbaseTransaction (
    val id: String,
    val accountId: String,
    val counterpartyAccount: String,
    val counterpartyName: String,
    val counterPartyLogoPath: String?,
    val instructedAmount: BigDecimal,
    val instructedCurrency: String,
    val transactionAmount: BigDecimal,
    val transactionCurrency: String,
    val transactionType: String,
    val description: String
)

