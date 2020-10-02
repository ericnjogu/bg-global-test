package com.mugowanjogu.banking.service

import com.mugowanjogu.banking.config.AppConfig
import com.mugowanjogu.banking.dto.OpbTransaction
import com.mugowanjogu.banking.service.DefaultBackbaseTransactionServiceUnitTest.Companion.getTestOpbTransaction
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.math.BigDecimal


@SpringJUnitWebConfig(classes = [AppConfig::class])
internal class DefaultBackbaseTransactionServiceWebTest {

    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var backbaseTransactionService: DefaultBackbaseTransactionService

    @BeforeEach
    fun setUp(wac: WebApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
        backbaseTransactionService.opbTransactionService = mockk {
            every { transactionList(any())} returns {
                val list = mutableListOf<OpbTransaction>()
                for (i in 1..10) {
                    list.add(getTestOpbTransaction())
                }
                list.toList()
            }()
            every {transactionsByType(any(), any())} returns {
                val list = mutableListOf<OpbTransaction>()
                for (i in 1..5) {
                    list.add(getTestOpbTransaction())
                }
                list.toList()
            }()
            every { totalForTransactionsType(any(), any()) } returns BigDecimal(16.27)
        }
    }

    @Test
    fun `transactionList() should fetch expected transactions`() {
        mockMvc.get("/account/transactions/{accountId}/list", "test-id") {
            accept = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect {
                status { isOk }
        }
    }

    @Test
    fun `transactionsByType() should fetch expected transactions`(){
        mockMvc.get("/account/transactions/{accountId}/type/{type}/list", "test-id", "hepa") {
            accept = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect {
                status { isOk }
        }
    }

    @Test
    fun `totalForTransactionsType() should produce expected total`() {
        mockMvc.get("/account/{accountId}/type/{type}/total", "test-id", "hepa") {
            accept = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect {
                status { isOk }
        }
    }
}