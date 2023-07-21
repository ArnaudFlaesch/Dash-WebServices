package com.dash.app.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.domain.model.config.ImportData
import io.restassured.RestAssured.defaultParser
import io.restassured.RestAssured.given
import io.restassured.http.Header
import io.restassured.http.Headers
import io.restassured.parsing.Parser
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DashConfigControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    private val CONFIG_ENDPOINT = "/dashConfig/"

    @BeforeAll
    fun testUp() {
        defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
    }

    @Test
    fun testExportConfig() {
        val exportData = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .`when`()
            .get("${CONFIG_ENDPOINT}export")
            .then().log().all()
            .statusCode(200)
            .log().all()
            .body("$", Matchers.notNullValue())
            .extract().`as`(ImportData::class.java)
        assertEquals(2, exportData.tabs.size)
        assertEquals(2, exportData.widgets.size)

        val exportedWidget = exportData.widgets[0]
        assertEquals(1, exportedWidget.type)
        assertEquals(1, exportedWidget.widgetOrder)
    }

    @Test
    fun testImportConfig() {
        val response = given()
            .multiPart("file", ClassPathResource("./files/dashboardConfigTest.json").file)
            .port(port)
            .headers(Headers(createAuthenticationHeader(jwtToken), Header("content-type", "multipart/form-data")))
            .`when`()
            .post("${CONFIG_ENDPOINT}import").then().log().all()
            .statusCode(200)
            .extract().`as`(Boolean::class.java)
        assertTrue(response)
    }
}
