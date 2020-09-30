package com.mugowanjogu.banking.service

import com.mugowanjogu.banking.dto.OpbTransaction
import com.mugowanjogu.banking.dto.OpbTransactionList
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
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
        val restTemplate = RestTemplate()

        return try {
            val response = restTemplate.getForEntity<OpbTransactionList>(
                    url = txUrl,
                    accountId
            )
            response.body.transactions
        } catch (e: HttpClientErrorException) {
            log.error(e)
            emptyList()
        }
    }

    override fun transactionsByType(accountId: String, type: String): List<OpbTransaction> {
        val list = transactionList(accountId = accountId)
        return list.filter { it.details.type == type }
    }

    override fun totalForTransactionsType(accountId: String, type: String): BigDecimal {
        val list = transactionsByType(accountId = accountId, type = type)
        var total = BigDecimal.ZERO
        list.forEach { total = total.add(it.details.value.amount) }

        return total
    }
}