package com.cashmanager.app.controller

import com.cashmanager.controller.requests.InsertExpensePayload
import com.cashmanager.entity.Expense
import com.cashmanager.entity.Label
import com.cashmanager.model.TotalExpenseByMonth
import com.cashmanager.utils.Constants
import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import io.restassured.RestAssured.defaultParser
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.http.Header
import io.restassured.parsing.Parser
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.containsInRelativeOrder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExpenseControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    private val EXPENSE_ENDPOINT = "/expense/"

    private var authorizationHeader: Header? = null

    @BeforeAll
    fun testUp() {
        defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdmin(port).accessToken
        authorizationHeader = createAuthenticationHeader(jwtToken)
    }

    @Test
    fun testAllExpenses() {
        val startIntervalDate = "2022-02-01"
        val endIntervalDate = "2022-05-25"
        val expenses: List<Expense> = given().port(port)
            .header(authorizationHeader)
            .param("startIntervalDate", startIntervalDate)
            .param("endIntervalDate", endIntervalDate)
            .`when`().get(EXPENSE_ENDPOINT)
            .then().log().all()
            .statusCode(200)
            .log().all()
            .extract()
            .`as`(object : TypeRef<List<Expense>>() {})
        assertEquals(4, expenses.size)
        assertThat(expenses.map(Expense::label).map(Label::label), containsInRelativeOrder("Restaurant", "Courses", "Courses", "Courses"))
    }

    @Test
    fun testGetTotalExpensesByMonth() {
        val totalExpensesByMonth: List<TotalExpenseByMonth> = given().port(port)
            .header(authorizationHeader)
            .`when`().get("${EXPENSE_ENDPOINT}getTotalExpensesByMonth")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .extract()
            .`as`(object : TypeRef<List<TotalExpenseByMonth>>() {})
        assertEquals(3, totalExpensesByMonth.size)
        assertThat(totalExpensesByMonth.map(TotalExpenseByMonth::total), containsInAnyOrder(55.0F, 32.0F, 137.0F))
    }

    @Test
    fun testGetTotalExpensesByMonthByLabelId() {
        val labels: List<Label> = given().port(port)
            .header(createAuthenticationHeader(jwtToken))
            .`when`().get(Constants.LABEL_ENDPOINT)
            .then().log().all()
            .statusCode(200)
            .log().all()
            .extract().`as`(object : TypeRef<List<Label>>() {})

        assertEquals(2, labels.size)
        val labelId = labels.filter { label: Label -> label.label == "Courses" }[0].id

        val totalExpensesByMonth: List<TotalExpenseByMonth> = given().port(port)
            .header(authorizationHeader)
            .param("labelId", labelId)
            .`when`().get("${EXPENSE_ENDPOINT}getTotalExpensesByMonthByLabelId")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .extract()
            .`as`(object : TypeRef<List<TotalExpenseByMonth>>() {})
        assertEquals(2, totalExpensesByMonth.size)
        assertThat(totalExpensesByMonth.map(TotalExpenseByMonth::total), containsInAnyOrder(32.0F, 137.0F))
    }

    @Test
    fun expenseCrudTests() {
        val expenseToInsert = InsertExpensePayload(140F, LocalDate.parse("2022-03-03"), 1)
        val insertedExpense: Expense = given()
            .port(port)
            .header(authorizationHeader)
            .contentType(ContentType.JSON)
            .body(expenseToInsert)
            .`when`().post("${EXPENSE_ENDPOINT}addExpense/")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .extract()
            .`as`(Expense::class.java)
        assertNotNull(insertedExpense.id)
        assertEquals(expenseToInsert.amount, insertedExpense.amount)

        val expenseToUpdate = insertedExpense.copy(amount = 2000F)
        val updatedExpense: Expense = given()
            .port(port)
            .header(authorizationHeader)
            .contentType(ContentType.JSON)
            .body(expenseToUpdate)
            .`when`().patch("${EXPENSE_ENDPOINT}updateExpense/")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .extract()
            .`as`(Expense::class.java)
        assertEquals(expenseToUpdate.amount, updatedExpense.amount)

        given()
            .port(port)
            .header(authorizationHeader)
            .param("expenseId", updatedExpense.id)
            .`when`().delete("${EXPENSE_ENDPOINT}deleteExpense/")
            .then().log().all()
            .statusCode(200)
            .log().all()
    }
}
