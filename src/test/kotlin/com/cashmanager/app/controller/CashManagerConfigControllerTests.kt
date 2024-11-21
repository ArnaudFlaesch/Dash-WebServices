package com.cashmanager.app.controller

import com.cashmanager.domain.model.CashManagerImportData
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.common.utils.SqlData
import io.restassured.RestAssured.defaultParser
import io.restassured.http.Header
import io.restassured.http.Headers
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.parsing.Parser
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.io.ClassPathResource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SqlData
class CashManagerConfigControllerTests {
    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    companion object {
        const val CASH_MANAGER_CONFIG_ENDPOINT = "/cashManagerConfig/"
    }

    @BeforeAll
    fun testUp() {
        defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
    }

    @Test
    fun testExportConfig() {
        val exportData =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                get("${CASH_MANAGER_CONFIG_ENDPOINT}export")
            } Then {
                statusCode(200)
                    .body("$", Matchers.notNullValue())
            } Extract {
                `as`(CashManagerImportData::class.java)
            }
        assertEquals(2, exportData.labels.size)
        assertEquals(4, exportData.expenses.size)

        val exportedExpense = exportData.expenses[0]
        assertNotNull(exportedExpense.id)
        assertEquals(100.0F, exportedExpense.amount)
    }

    @Test
    fun testImportConfig() {
        val response =
            Given {
                multiPart("file", ClassPathResource("./files/cashManagerData.json").file)
                    .port(port)
                    .headers(
                        Headers(
                            createAuthenticationHeader(jwtToken),
                            Header("content-type", "multipart/form-data")
                        )
                    )
            } When {
                post("${CASH_MANAGER_CONFIG_ENDPOINT}import")
            } Then {
                statusCode(200)
            } Extract {
                `as`(Boolean::class.java)
            }
        assertTrue(response)
    }
}
