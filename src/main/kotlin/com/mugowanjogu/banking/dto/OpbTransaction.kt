package com.mugowanjogu.banking.dto

import java.math.BigDecimal

data class OpbTransaction (
    val id: String,
    val this_account: ThisAccount,
    val other_account: OtherAccount,
    val details: AccountDetails
)

data class ThisAccount (
    val id: String
)

data class OtherAccount (
    val number: String,
    val holder: AccountHolder,
    val metadata: AccountMetadata
)

data class AccountHolder (
    val name: String
)

data class AccountMetadata (
    val image_URL: String?
)

data class AccountDetails (
    val value: TxValue,
    val type: String,
    val description: String
)

data class TxValue (
    val amount: BigDecimal,
    val currency: String
)

