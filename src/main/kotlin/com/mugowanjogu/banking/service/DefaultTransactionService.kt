package com.mugowanjogu.banking.service

import com.mugowanjogu.banking.dto.OpbTransaction
import com.mugowanjogu.banking.dto.OpbTransactionList
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.math.BigDecimal

@Service
class DefaultTransactionService: TransactionService {

    companion object {
        val log = LogFactory.getLog(DefaultTransactionService::class.java)
    }

    @Value("\${opb.tx.url}")
    lateinit var txUrl: String

    override fun transactionList(accountId: String): List<OpbTransaction> {
        log.debug("getting transaction list from '$txUrl'")
        val restTemplate = RestTemplate()
        val response = restTemplate.getForEntity<OpbTransactionList>(
                url = txUrl,
                accountId
        )

        return response.body.transactions
    }

    override fun transactionsByType(accountId: String, type: String): List<OpbTransaction> {
        TODO("Not yet implemented")
    }

    override fun totalForTransactionsType(accountId: String, type: String): BigDecimal {
        TODO("Not yet implemented")
    }
}