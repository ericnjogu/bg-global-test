package com.mugowanjogu.banking.service

import com.mugowanjogu.banking.dto.BackbaseTransaction
import com.mugowanjogu.banking.dto.OpbTransaction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/account")
class DefaultBackbaseTransactionService: BackbaseTransactionService {

    companion object {
        val log: Log = LogFactory.getLog(DefaultBackbaseTransactionService::class.java)
    }

    @Autowired
    lateinit var opbTransactionService: OpbTransactionService

    @GetMapping("/transactions/{accountId}/list")
    override fun transactionList(@PathVariable accountId: String): List<BackbaseTransaction> {
        log.info("getting transaction list for account $accountId")
        return opbTransactionService.transactionList(accountId).map {
            tx -> mapOpbToBackbaseTransaction(tx)
        }
    }

    @GetMapping("/transactions/{accountId}/type/{type}/list")
    override fun transactionsByType(@PathVariable accountId: String, @PathVariable type: String): List<BackbaseTransaction> {
        log.info("getting transactions by type for account '$accountId',  type '$type'")
        return opbTransactionService.transactionsByType(accountId, type).map {
            tx -> mapOpbToBackbaseTransaction(tx)
        }
    }

    @GetMapping("/{accountId}/type/{type}/total")
    override fun totalForTransactionsType(@PathVariable accountId: String, @PathVariable type: String): BigDecimal {
        return opbTransactionService.totalForTransactionsType(accountId, type)
    }

    /**
     * map OPB transaction to backbase transaction
     */
    fun mapOpbToBackbaseTransaction(opbTx: OpbTransaction): BackbaseTransaction {
        return BackbaseTransaction(
            id = opbTx.id,
            accountId = opbTx.this_account.id,
            counterpartyAccount = opbTx.other_account.number,
            counterpartyName = opbTx.other_account.holder.name,
            counterPartyLogoPath = opbTx.other_account.metadata.image_URL,
            instructedAmount = opbTx.details.value.amount,
            instructedCurrency = opbTx.details.value.currency,
            transactionAmount = opbTx.details.value.amount,
            transactionCurrency = opbTx.details.value.currency,
            transactionType = opbTx.details.type,
            description = opbTx.details.description
        )
    }
}