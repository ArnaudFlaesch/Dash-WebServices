package com.cashmanager.app.controller

import com.cashmanager.app.controller.requests.InsertExpensePayload
import com.cashmanager.domain.model.ExpenseDomain
import com.cashmanager.domain.model.LabelDomain
import com.cashmanager.domain.model.TotalExpenseByMonthDomain
import com.cashmanager.utils.Constants
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.common.utils.SqlData
import io.restassured.RestAssured.defaultParser
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.http.Header
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
        const val EXPENSE_ENDPOINT = "/expense/"
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
        val expenses: List<ExpenseDomain> =
            given()
                .port(port)
                .header(authorizationHeader)
                .param("startIntervalDate", startIntervalDate)
                .param("endIntervalDate", endIntervalDate)
                .`when`()
                .get(EXPENSE_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .`as`(object : TypeRef<List<ExpenseDomain>>() {})
        assertEquals(4, expenses.size)
    }

    @Test
    fun testGetTotalExpensesByMonth() {
        val totalExpensesByMonth: List<TotalExpenseByMonthDomain> =
            given()
                .port(port)
                .header(authorizationHeader)
                .`when`()
                .get("${EXPENSE_ENDPOINT}getTotalExpensesByMonth")
                .then()
                .statusCode(200)
                .extract()
                .`as`(object : TypeRef<List<TotalExpenseByMonthDomain>>() {})
        assertEquals(3, totalExpensesByMonth.size)
        assertThat(
            totalExpensesByMonth.map(TotalExpenseByMonthDomain::total),
            containsInAnyOrder(55.0F, 32.0F, 137.0F)
        )
    }

    @Test
    fun testGetTotalExpensesByMonthByLabelId() {
        val labels: List<LabelDomain> =
            given()
                .port(port)
                .header(createAuthenticationHeader(jwtToken))
                .`when`()
                .get(Constants.LABEL_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .`as`(object : TypeRef<List<LabelDomain>>() {})

        assertEquals(2, labels.size)
        val labelId = labels.filter { label: LabelDomain -> label.label == "Courses" }[0].id

        val totalExpensesByMonth: List<TotalExpenseByMonthDomain> =
            given()
                .port(port)
                .header(authorizationHeader)
                .param("labelId", labelId)
                .`when`()
                .get("${EXPENSE_ENDPOINT}getTotalExpensesByMonthByLabelId")
                .then()
                .statusCode(200)
                .extract()
                .`as`(object : TypeRef<List<TotalExpenseByMonthDomain>>() {})
        assertEquals(2, totalExpensesByMonth.size)
        assertThat(
            totalExpensesByMonth.map(TotalExpenseByMonthDomain::total),
            containsInAnyOrder(32.0F, 137.0F)
        )
    }

    @Test
    fun expenseCrudTests() {
        val expenseToInsert = InsertExpensePayload(140F, LocalDate.parse("2022-03-03"), 1)
        val insertedExpense: ExpenseDomain =
            given()
                .port(port)
                .header(authorizationHeader)
                .contentType(ContentType.JSON)
                .body(expenseToInsert)
                .`when`()
                .post("${EXPENSE_ENDPOINT}addExpense")
                .then()
                .statusCode(200)
                .extract()
                .`as`(ExpenseDomain::class.java)
        assertNotNull(insertedExpense.id)
        assertEquals(expenseToInsert.amount, insertedExpense.amount)

        val expenseToUpdate = insertedExpense.copy(amount = 2000F)
        val updatedExpense: ExpenseDomain =
            given()
                .port(port)
                .header(authorizationHeader)
                .contentType(ContentType.JSON)
                .body(expenseToUpdate)
                .`when`()
                .patch("${EXPENSE_ENDPOINT}updateExpense")
                .then()
                .statusCode(200)
                .extract()
                .`as`(ExpenseDomain::class.java)
        assertEquals(expenseToUpdate.amount, updatedExpense.amount)

        given()
            .port(port)
            .header(authorizationHeader)
            .param("expenseId", updatedExpense.id)
            .`when`()
            .delete("${EXPENSE_ENDPOINT}deleteExpense")
            .then()
            .log()
            .all()
            .statusCode(200)
            .log()
            .all()
    }
}
