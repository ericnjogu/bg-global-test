package com.mugowanjogu.banking.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mugowanjogu.banking.dto.OpbTransaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DefaultBackbaseTransactionServiceUnitTest {

    lateinit var defaultBackbaseTransactionService: DefaultBackbaseTransactionService
    companion object {
        fun getTestOpbTransaction(): OpbTransaction {
            val opbJson = """
                {
                  "id": "e22b7066-d02f-41fa-a84f-5dbfcc39e307",
                  "this_account": {
                    "id": "savings-kids-john",
                    "holders": [
                      {
                        "name": "Savings - Kids John",
                        "is_alias": false
                      }
                    ],
                    "number": "832425-00304050",
                    "kind": "savings",
                    "IBAN": null,
                    "swift_bic": null,
                    "bank": {
                      "national_identifier": "rbs",
                      "name": "The Royal Bank of Scotland"
                    }
                  },
                  "other_account": {
                    "id": "5780MRN4uBIgWYmWAhI3pdqbSpItvOw4culXP5FWHJA",
                    "holder": {
                      "name": "ALIAS_03C57D",
                      "is_alias": true
                    },
                    "number": "savings-kids-john",
                    "kind": "AC",
                    "IBAN": "4930396",
                    "swift_bic": null,
                    "bank": {
                      "national_identifier": null,
                      "name": "rbs"
                    },
                    "metadata": {
                      "public_alias": null,
                      "private_alias": null,
                      "more_info": null,
                      "URL": null,
                      "image_URL": null,
                      "open_corporates_URL": null,
                      "corporate_location": null,
                      "physical_location": null
                    }
                  },
                  "details": {
                    "type": "SEPA",
                    "description": "This is a SEPA Transaction Request",
                    "posted": "2020-06-05T08:15:58Z",
                    "completed": "2020-06-05T08:15:58Z",
                    "new_balance": {
                      "currency": "GBP",
                      "amount": null
                    },
                    "value": {
                      "currency": "GBP",
                      "amount": "8.60"
                    }
                  },
                  "metadata": {
                    "narrative": null,
                    "comments": [],
                    "tags": [],
                    "images": [],
                    "where": null
                  }
                }
        """.trimIndent()
            // using jacksonObjectMapper instead of ObjectMapper to avoid errors related to missing constructor
            // https://stackoverflow.com/a/53191565/315385
            val objectMapper = jacksonObjectMapper()
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            return objectMapper.readValue(opbJson, OpbTransaction::class.java)
        }
    }
    @BeforeEach
    fun setUp() {
        defaultBackbaseTransactionService = DefaultBackbaseTransactionService()
    }

    @Test
    fun mapOpbToBackbaseTransaction() {
        val bbTx = defaultBackbaseTransactionService.mapOpbToBackbaseTransaction(
            getTestOpbTransaction()
        )
        assertEquals("e22b7066-d02f-41fa-a84f-5dbfcc39e307", bbTx.id, "id")
        assertEquals("savings-kids-john", bbTx.accountId, "accountId")
        assertEquals("savings-kids-john", bbTx.counterpartyAccount, "counterpartyAccount")
        assertEquals("ALIAS_03C57D", bbTx.counterpartyName, "counterpartyName")
        assertEquals("savings-kids-john", bbTx.counterpartyAccount, "counterpartyAccount")
        assertNull(bbTx.counterPartyLogoPath, "counterPartyLogoPath")
        assertEquals("8.60", bbTx.instructedAmount.toString(), "instructedAmount")
        assertEquals("GBP", bbTx.instructedCurrency, "instructedCurrency")
        assertEquals("8.60", bbTx.transactionAmount.toString(), "transactionAmount")
        assertEquals("GBP", bbTx.transactionCurrency, "transactionCurrency")
        assertEquals("8.60", bbTx.instructedAmount.toString(), "instructedAmount")
        assertEquals("SEPA", bbTx.transactionType, "transactionType")
        assertEquals("8.60", bbTx.instructedAmount.toString(), "instructedAmount")
        assertEquals("This is a SEPA Transaction Request", bbTx.description, "description")
    }
}