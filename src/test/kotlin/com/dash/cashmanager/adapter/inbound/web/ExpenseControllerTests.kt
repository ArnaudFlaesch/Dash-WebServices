package com.dash.cashmanager.adapter.inbound.web

import com.dash.cashmanager.adapter.inbound.web.request.InsertExpenseRequest
import com.dash.cashmanager.domain.model.Expense
import com.dash.cashmanager.domain.model.Label
import com.dash.cashmanager.domain.model.TotalExpenseByMonth
import com.dash.cashmanager.utils.Constants
import com.dash.utils.IntegrationTestsUtils
import com.dash.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.utils.SqlData
import io.restassured.RestAssured.defaultParser
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.http.Header
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.parsing.Parser
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SqlData
class ExpenseControllerTests {
    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    companion object {
        const val EXPENSE_ENDPOINT = "/expenses"
    }

    private var authorizationHeader: Header? = null

    @BeforeAll
    fun testUp() {
        defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
        authorizationHeader = createAuthenticationHeader(jwtToken)
    }

    @Test
    fun testAllExpenses() {
        val startIntervalDate = "2022-02-01"
        val endIntervalDate = "2022-05-25"
        val expenses: List<Expense> =
            Given {
                port(port)
                    .header(authorizationHeader)
                    .param("startIntervalDate", startIntervalDate)
                    .param("endIntervalDate", endIntervalDate)
            } When {
                get(EXPENSE_ENDPOINT)
            } Then {
                statusCode(200)
            } Extract {
                `as`(object : TypeRef<List<Expense>>() {})
            }
        assertEquals(4, expenses.size)
    }

    @Test
    fun testGetTotalExpensesByMonth() {
        val totalExpensesByMonth: List<TotalExpenseByMonth> =
            Given {
                port(port)
                    .header(authorizationHeader)
            } When {
                get("$EXPENSE_ENDPOINT/monthly-totals")
            } Then {
                statusCode(200)
            } Extract {
                `as`(object : TypeRef<List<TotalExpenseByMonth>>() {})
            }
        assertEquals(3, totalExpensesByMonth.size)
        assertThat(
            totalExpensesByMonth.map(TotalExpenseByMonth::total),
            containsInAnyOrder(55.0F, 32.0F, 137.0F)
        )
    }

    @Test
    fun testGetTotalExpensesByMonthByLabelId() {
        val labels: List<Label> =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                get(Constants.LABEL_ENDPOINT)
            } Then {
                statusCode(200)
            } Extract {
                `as`(object : TypeRef<List<Label>>() {})
            }

        assertEquals(2, labels.size)
        val labelId = labels.filter { label: Label -> label.label == "Courses" }[0].id

        val totalExpensesByMonth: List<TotalExpenseByMonth> =
            Given {
                port(port)
                    .header(authorizationHeader)
                    .param("labelId", labelId)
            } When {
                get("$EXPENSE_ENDPOINT/monthly-totals")
            } Then {
                statusCode(200)
            } Extract {
                `as`(object : TypeRef<List<TotalExpenseByMonth>>() {})
            }
        assertEquals(2, totalExpensesByMonth.size)
        assertThat(
            totalExpensesByMonth.map(TotalExpenseByMonth::total),
            containsInAnyOrder(32.0F, 137.0F)
        )
    }

    @Test
    fun expenseCrudTests() {
        val expenseToInsert = InsertExpenseRequest(140F, LocalDate.parse("2022-03-03"), 1)
        val insertedExpense: Expense =
            Given {
                port(port)
                    .header(authorizationHeader)
                    .contentType(ContentType.JSON)
                    .body(expenseToInsert)
            } When {
                post(EXPENSE_ENDPOINT)
            } Then {
                statusCode(201)
            } Extract {
                `as`(Expense::class.java)
            }
        assertNotNull(insertedExpense.id)
        assertEquals(expenseToInsert.amount, insertedExpense.amount)

        val expenseToUpdate = insertedExpense.copy(amount = 2000F)
        val updatedExpense: Expense =
            Given {
                port(port)
                    .header(authorizationHeader)
                    .contentType(ContentType.JSON)
                    .body(expenseToUpdate)
            } When {
                put("$EXPENSE_ENDPOINT/${insertedExpense.id}")
            } Then {
                statusCode(200)
            } Extract {
                `as`(Expense::class.java)
            }
        assertEquals(expenseToUpdate.amount, updatedExpense.amount)
        assertEquals(insertedExpense.id, updatedExpense.id)

        Given {
            port(port)
                .header(authorizationHeader)
        } When {
            delete("$EXPENSE_ENDPOINT/${updatedExpense.id}")
        } Then {
            log()
                .all()
                .statusCode(204)
                .log()
                .all()
        }
    }
}
