package com.cashmanager.controller

import com.cashmanager.controller.requests.InsertExpensePayload
import com.cashmanager.entity.Expense
import com.cashmanager.entity.Label
import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
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
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExpenseControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0

    private var jwtToken: String? = null

    private val EXPENSE_ENDPOINT = "/expense/"

    @BeforeAll
    fun testUp() {
        defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdmin(port).accessToken
    }

    @Test
    fun testAllExpenses() {
        val startIntervalDate = "2022-02-01"
        val endIntervalDate = "2022-05-01"
        val expenses: List<Expense> = given().port(port)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .param("startIntervalDate", startIntervalDate)
            .param("endIntervalDate", endIntervalDate)
            .`when`().get("$EXPENSE_ENDPOINT")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .extract()
            .`as`(object : TypeRef<List<Expense>>() {})
        assertEquals(3, expenses.size)
        assertThat(expenses.map(Expense::label).map(Label::label), containsInAnyOrder("Courses", "Courses", "Restaurant"))
    }

    @Test
    fun expenseCrudTests() {
        val expenseToInsert = InsertExpensePayload(140, LocalDate.parse("2022-03-03"), 1)
        val insertedExpense: Expense = given()
            .port(port)
            .header(Header("Authorization", "Bearer $jwtToken"))
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

        val expenseToUpdate = insertedExpense.copy(amount = 2000)
        val updatedExpense: Expense = given()
            .port(port)
            .header(Header("Authorization", "Bearer $jwtToken"))
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
            .header(Header("Authorization", "Bearer $jwtToken"))
            .param("expenseId", updatedExpense.id)
            .`when`().delete("${EXPENSE_ENDPOINT}deleteExpense/")
            .then().log().all()
            .statusCode(200)
            .log().all()
    }
}
