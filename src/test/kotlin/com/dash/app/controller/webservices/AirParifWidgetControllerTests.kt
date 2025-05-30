package com.dash.app.controller.webservices

import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.domain.model.airParif.AirParifColor
import com.dash.domain.model.airParif.AirParifPrevisionEnum
import com.dash.domain.model.airParif.Prevision
import io.restassured.RestAssured
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
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
class AirParifWidgetControllerTests {
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
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .accept(ContentType.JSON)
            } When {
                param("commune", communeInseeCode)
                    .get("$airParifWidgetEndpoint/previsionCommune")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(object : TypeRef<List<Prevision>>() {})
            }

        assertEquals(getPrevisionsDataResponse.size, 2)
    }

    @Test
    fun testGetColorsData() {
        val getColorsResponse =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .accept(ContentType.JSON)
            } When {
                get("$airParifWidgetEndpoint/couleurs")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(object : TypeRef<List<AirParifColor>>() {})
            }

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
