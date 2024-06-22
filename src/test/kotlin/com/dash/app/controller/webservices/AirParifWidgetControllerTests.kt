package com.dash.app.controller.webservices

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.domain.model.airParif.AirParifColor
import com.dash.domain.model.airParif.AirParifPrevisionEnum
import com.dash.domain.model.airParif.Prevision
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.parsing.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AirParifWidgetControllerTests : AbstractIT() {
    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    private val airParifWidgetEndpoint = "/airParifWidget"

    @BeforeAll
    fun setup() {
        RestAssured.defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
    }

    @Test
    fun testGetPrevisionsData() {
        val communeInseeCode = "75112"

        val getPrevisionsDataResponse =
            given()
                .port(port)
                .header(createAuthenticationHeader(jwtToken))
                .accept(ContentType.JSON)
                .`when`()
                .param("commune", communeInseeCode)
                .get("$airParifWidgetEndpoint/previsionCommune")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .`as`(object : TypeRef<List<Prevision>>() {})

        assertEquals(getPrevisionsDataResponse.size, 2)
    }

    @Test
    fun testGetColorsData() {
        val getColorsResponse =
            given()
                .port(port)
                .header(createAuthenticationHeader(jwtToken))
                .accept(ContentType.JSON)
                .`when`()
                .get("$airParifWidgetEndpoint/couleurs")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .`as`(object : TypeRef<List<AirParifColor>>() {})

        val expected =
            listOf(
                AirParifColor(AirParifPrevisionEnum.BON, "#50f0e6"),
                AirParifColor(AirParifPrevisionEnum.MOYEN, "#50ccaa"),
                AirParifColor(AirParifPrevisionEnum.DEGRADE, "#f0e641"),
                AirParifColor(AirParifPrevisionEnum.MAUVAIS, "#ff5050"),
                AirParifColor(AirParifPrevisionEnum.TRES_MAUVAIS, "#960032"),
                AirParifColor(AirParifPrevisionEnum.EXTREMEMENT_MAUVAIS, "#7d2181")
            )

        assertEquals(expected, getColorsResponse)
    }
}
