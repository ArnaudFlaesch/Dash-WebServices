package com.dash.app.controller

import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.common.utils.SqlData
import com.dash.app.controller.requests.widget.CreateMiniWidgetPayload
import com.dash.domain.model.MiniWidgetDomain
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
@SqlData
class MiniWidgetControllerTests {
    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    companion object {
        const val MINI_WIDGET_ENDPOINT = "/miniWidget/"
    }

    @BeforeAll
    fun testUp() {
        defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
    }

    @Test
    fun insertWidgetToDatabase() {
        val widget = CreateMiniWidgetPayload(1)

        val insertedMiniWidgetDomain: MiniWidgetDomain =
            given()
                .contentType(ContentType.JSON)
                .header(createAuthenticationHeader(jwtToken))
                .port(port)
                .body(widget)
                .`when`()
                .post("${MINI_WIDGET_ENDPOINT}addMiniWidget")
                .then()
                .log()
                .all()
                .statusCode(200)
                .extract()
                .`as`(MiniWidgetDomain::class.java)

        assertNotNull(insertedMiniWidgetDomain.id)
        assertEquals(insertedMiniWidgetDomain.type, widget.type)

        val widgetDomainList =
            given()
                .port(port)
                .header(createAuthenticationHeader(jwtToken))
                .`when`()
                .get(MINI_WIDGET_ENDPOINT)
                .then()
                .log()
                .all()
                .statusCode(200)
                .log()
                .all()
                .extract()
                .`as`(object : TypeRef<List<MiniWidgetDomain>>() {})
        assertEquals(1, widgetDomainList.size)

        val updatedMiniWidgetDomain: MiniWidgetDomain =
            given()
                .header(createAuthenticationHeader(jwtToken))
                .contentType(ContentType.JSON)
                .port(port)
                .body(insertedMiniWidgetDomain.copy(data = "{}"))
                .`when`()
                .patch("${MINI_WIDGET_ENDPOINT}updateWidgetData/${insertedMiniWidgetDomain.id}")
                .then()
                .log()
                .all()
                .statusCode(200)
                .extract()
                .`as`(MiniWidgetDomain::class.java)

        assertNotNull(updatedMiniWidgetDomain.id)
        assertEquals("{}", updatedMiniWidgetDomain.data)
        assertNotNull(updatedMiniWidgetDomain.userId)

        val updatedWidgetListDomain =
            given()
                .header(createAuthenticationHeader(jwtToken))
                .port(port)
                .`when`()
                .get(MINI_WIDGET_ENDPOINT)
                .then()
                .log()
                .all()
                .statusCode(200)
                .log()
                .all()
                .extract()
                .`as`(object : TypeRef<List<MiniWidgetDomain>>() {})
        assertEquals(1, updatedWidgetListDomain.size)

        given()
            .header(createAuthenticationHeader(jwtToken))
            .port(port)
            .`when`()
            .param("widgetId", insertedMiniWidgetDomain.id)
            .delete("${MINI_WIDGET_ENDPOINT}deleteMiniWidget")
            .then()
            .log()
            .all()
            .statusCode(200)
            .log()
            .all()

        val updatedMiniWidgetList =
            given()
                .port(port)
                .header(createAuthenticationHeader(jwtToken))
                .`when`()
                .get(MINI_WIDGET_ENDPOINT)
                .then()
                .log()
                .all()
                .statusCode(200)
                .log()
                .all()
                .extract()
                .`as`(object : TypeRef<List<MiniWidgetDomain>>() {})
        assertEquals(0, updatedMiniWidgetList.size)
    }
}
