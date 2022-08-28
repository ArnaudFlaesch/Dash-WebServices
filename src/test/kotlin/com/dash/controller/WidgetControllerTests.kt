package com.dash.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.entity.Tab
import com.dash.entity.Widget
import com.dash.repository.TabDataset
import io.restassured.RestAssured.defaultParser
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.http.Header
import io.restassured.parsing.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TabDataset
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WidgetControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    private val WIDGET_ENDPOINT = "/widget/"

    @BeforeAll
    fun testUp() {
        defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdmin(port).accessToken
    }

    @Test
    fun testGetAllWidgetsByTabId() {
        val widgetList = given().port(port)
            .header(createAuthenticationHeader(jwtToken))
            .param("tabId", 1)
            .`when`().get(WIDGET_ENDPOINT)
            .then().log().all()
            .statusCode(200)
            .log().all()
            .extract().`as`(object : TypeRef<List<Widget>>() {})
        assertEquals(0, widgetList.size)
    }

    @Test
    fun insertWidgetToDatabase() {
        val tab = Tab(10, "", 1)
        val widget = Widget(0, 2, "{}", 1, tab)

        val insertedWidget: Widget = given()
            .contentType(ContentType.JSON)
            .header(createAuthenticationHeader(jwtToken))
            .port(port)
            .body(widget)
            .`when`()
            .post("${WIDGET_ENDPOINT}addWidget/")
            .then().log().all()
            .statusCode(200)
            .extract().`as`(Widget::class.java)

        assertNotNull(insertedWidget.id)
        assertEquals(insertedWidget.type, widget.type)

        val widgetList = given().port(port)
            .header(createAuthenticationHeader(jwtToken))
            .param("tabId", 10)
            .`when`().get(WIDGET_ENDPOINT).then().log().all()
            .statusCode(200).log().all()
            .extract().`as`(object : TypeRef<List<Widget>>() {})
        assertEquals(1, widgetList.size)

        val updatedWidget: Widget = given()
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .port(port)
            .body(insertedWidget.copy(widgetOrder = 0)).`when`().patch("${WIDGET_ENDPOINT}updateWidgetData/${insertedWidget.id}")
            .then().log().all()
            .statusCode(200)
            .extract().`as`(Widget::class.java)

        assertEquals(insertedWidget.data, updatedWidget.data)

        given()
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .port(port)
            .param("id", updatedWidget.id).`when`().delete("${WIDGET_ENDPOINT}deleteWidget/")
            .then().log().all()
            .statusCode(200)

        val updatedWidgetList = given()
            .header(createAuthenticationHeader(jwtToken))
            .port(port)
            .param("tabId", 10)
            .`when`().get(WIDGET_ENDPOINT).then().log().all()
            .statusCode(200).log().all()
            .extract().`as`(object : TypeRef<List<Widget>>() {})
        assertEquals(0, updatedWidgetList.size)
    }

    @Test
    fun testUpdateWidgetsOrder() {
        val tab = Tab(10, "", 1)
        val firstWidget = Widget(0, 2, "{}", 1, tab = tab)
        val secondWidget = Widget(0, 3, "{}", 2, tab = tab)

        val firstInsertedWidget: Widget = given()
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .port(port)
            .body(firstWidget).`when`().post("${WIDGET_ENDPOINT}addWidget/")
            .then().log().all()
            .statusCode(200)
            .extract().`as`(Widget::class.java)

        val secondInsertedWidget: Widget = given()
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .port(port)
            .body(secondWidget).`when`().post("${WIDGET_ENDPOINT}addWidget/")
            .then().log().all()
            .statusCode(200)
            .extract().`as`(Widget::class.java)

        assertNotNull(firstInsertedWidget.id)
        assertEquals(firstInsertedWidget.type, firstWidget.type)

        assertNotNull(secondInsertedWidget.id)
        assertEquals(secondInsertedWidget.type, secondWidget.type)

        val updatedWidgets: List<Widget> = given()
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .port(port)
            .body(listOf(firstInsertedWidget.copy(widgetOrder = 2), secondInsertedWidget.copy(widgetOrder = 3)))
            .`when`().post("${WIDGET_ENDPOINT}updateWidgetsOrder/")
            .then().log().all()
            .statusCode(200)
            .extract().`as`(object : TypeRef<List<Widget>>() {})

        assertEquals(2, updatedWidgets.size)
        assertEquals(2, updatedWidgets[0].widgetOrder)
        assertEquals(2, updatedWidgets[0].type)
        assertEquals(3, updatedWidgets[1].widgetOrder)
        assertEquals(3, updatedWidgets[1].type)
    }
}
