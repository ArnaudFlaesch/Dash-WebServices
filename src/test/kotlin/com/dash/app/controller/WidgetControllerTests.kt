package com.dash.app.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.app.controller.requests.CreateWidgetPayload
import com.dash.domain.model.WidgetDomain
import io.restassured.RestAssured.defaultParser
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
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
        val widgetEntityList = given().port(port)
            .header(createAuthenticationHeader(jwtToken))
            .param("tabId", 1)
            .`when`().get(WIDGET_ENDPOINT)
            .then().log().all()
            .statusCode(200)
            .log().all()
            .extract().`as`(object : TypeRef<List<WidgetDomain>>() {})
        assertEquals(1, widgetEntityList.size)
    }

    @Test
    fun insertWidgetToDatabase() {
        val widget = CreateWidgetPayload(2, 1)

        val insertedWidgetEntity: WidgetDomain = given()
            .contentType(ContentType.JSON)
            .header(createAuthenticationHeader(jwtToken))
            .port(port)
            .body(widget)
            .`when`()
            .post("${WIDGET_ENDPOINT}addWidget/")
            .then().log().all()
            .statusCode(200)
            .extract().`as`(WidgetDomain::class.java)

        assertNotNull(insertedWidgetEntity.id)
        assertEquals(insertedWidgetEntity.type, widget.type)

        val widgetEntityList = given().port(port)
            .header(createAuthenticationHeader(jwtToken))
            .param("tabId", 1)
            .`when`().get(WIDGET_ENDPOINT).then().log().all()
            .statusCode(200).log().all()
            .extract().`as`(object : TypeRef<List<WidgetDomain>>() {})
        assertEquals(2, widgetEntityList.size)

        val updatedWidgetEntity: WidgetDomain = given()
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .port(port)
            .body(insertedWidgetEntity.copy(widgetOrder = 0, data = "{}")).`when`().patch("${WIDGET_ENDPOINT}updateWidgetData/${insertedWidgetEntity.id}")
            .then().log().all()
            .statusCode(200)
            .extract().`as`(WidgetDomain::class.java)

        assertEquals("{}", updatedWidgetEntity.data)

        given()
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .port(port)
            .param("id", updatedWidgetEntity.id).`when`().delete("${WIDGET_ENDPOINT}deleteWidget/")
            .then().log().all()
            .statusCode(200)

        val updatedWidgetListEntity = given()
            .header(createAuthenticationHeader(jwtToken))
            .port(port)
            .param("tabId", 1)
            .`when`().get(WIDGET_ENDPOINT).then().log().all()
            .statusCode(200).log().all()
            .extract().`as`(object : TypeRef<List<WidgetDomain>>() {})
        assertEquals(1, updatedWidgetListEntity.size)
    }

    @Test
    fun testUpdateWidgetsOrder() {
        val firstWidget = CreateWidgetPayload(2, 1)
        val secondWidget = CreateWidgetPayload(3, 1)

        val firstInsertedWidgetEntity: WidgetDomain = given()
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .port(port)
            .body(firstWidget).`when`().post("${WIDGET_ENDPOINT}addWidget/")
            .then().log().all()
            .statusCode(200)
            .extract().`as`(WidgetDomain::class.java)

        val secondInsertedWidgetEntity: WidgetDomain = given()
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .port(port)
            .body(secondWidget).`when`().post("${WIDGET_ENDPOINT}addWidget/")
            .then().log().all()
            .statusCode(200)
            .extract().`as`(WidgetDomain::class.java)

        assertNotNull(firstInsertedWidgetEntity.id)
        assertEquals(firstInsertedWidgetEntity.type, firstWidget.type)

        assertNotNull(secondInsertedWidgetEntity.id)
        assertEquals(secondInsertedWidgetEntity.type, secondWidget.type)

        val updatedWidgetEntities: List<WidgetDomain> = given()
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .port(port)
            .body(listOf(firstInsertedWidgetEntity.copy(widgetOrder = 2), secondInsertedWidgetEntity.copy(widgetOrder = 3)))
            .`when`().post("${WIDGET_ENDPOINT}updateWidgetsOrder/")
            .then().log().all()
            .statusCode(200)
            .extract().`as`(object : TypeRef<List<WidgetDomain>>() {})

        assertEquals(2, updatedWidgetEntities.size)
        assertEquals(2, updatedWidgetEntities[0].widgetOrder)
        assertEquals(2, updatedWidgetEntities[0].type)
        assertEquals(3, updatedWidgetEntities[1].widgetOrder)
        assertEquals(3, updatedWidgetEntities[1].type)
    }
}
