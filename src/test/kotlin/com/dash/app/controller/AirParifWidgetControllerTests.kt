package com.dash.app.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.domain.model.airParif.AirParifColors
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.parsing.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withStatus
import org.springframework.web.client.RestTemplate
import java.net.URI

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AirParifWidgetControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0

    private lateinit var mockServer: MockRestServiceServer

    @Autowired
    private lateinit var restTemplate: RestTemplate

    private lateinit var jwtToken: String

    private val airParifWidgetEndpoint = "/airParifWidget"

    @BeforeAll
    fun setup() {
        RestAssured.defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdmin(port).accessToken
        mockServer = MockRestServiceServer.createServer(restTemplate)
    }

    @BeforeEach
    fun resetMockServer() {
        mockServer.reset()
    }

    @Test
    fun testGetPrevisionsData() {
        val communeInseeCode = "75101"

        val mockedResponse = "{\n" +
            "  \"$communeInseeCode\":" +
            "[\n" +
            "    {\n" +
            "      \"date\": \"2021-01-15\",\n" +
            "      \"no2\": \"Bon\",\n" +
            "      \"o3\": \"Mauvais\",\n" +
            "      \"pm10\": \"Moyen\",\n" +
            "      \"pm25\": \"Dégradé\",\n" +
            "      \"so2\": \"Bon\",\n" +
            "      \"indice\": \"Dégradé\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"date\": \"2021-01-16\",\n" +
            "      \"no2\": \"Bon\",\n" +
            "      \"o3\": \"Mauvais\",\n" +
            "      \"pm10\": \"Moyen\",\n" +
            "      \"pm25\": \"Dégradé\",\n" +
            "      \"so2\": \"Bon\",\n" +
            "      \"indice\": \"Dégradé\"\n" +
            "    }\n" +
            "  ]\n" +
            "}"

        mockServer.expect(ExpectedCount.once(), requestTo(URI("https://api.airparif.asso.fr/indices/prevision/commune?insee=$communeInseeCode")))
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON).body(mockedResponse)
            )

        val getPrevisionsDataResponse = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .accept(ContentType.JSON)
            .`when`()
            .param("commune", communeInseeCode)
            .get("$airParifWidgetEndpoint/previsionCommune")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().`as`(List::class.java)

        assertEquals(getPrevisionsDataResponse.size, 2)
        mockServer.verify()
    }

    @Test
    fun testGetColorsData() {
        val mockedResponse = "{\n" +
            "  \"Bon\": \"#50f0e6\",\n" +
            "  \"Moyen\": \"#50ccaa\",\n" +
            "  \"Dégradé\": \"#f0e641\",\n" +
            "  \"Mauvais\": \"#ff5050\",\n" +
            "  \"Très Mauvais\": \"#960032\",\n" +
            "  \"Extrêmement Mauvais\": \"#7d2181\"\n" +
            "}"

        mockServer.expect(ExpectedCount.once(), requestTo(URI("https://api.airparif.asso.fr/indices/prevision/couleurs")))
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON).body(mockedResponse)
            )

        val getColorsResponse = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .accept(ContentType.JSON)
            .`when`()
            .get("$airParifWidgetEndpoint/couleurs")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().`as`(AirParifColors::class.java)

        val expected = AirParifColors(
            "#50f0e6",
            "#50ccaa",
            "#f0e641",
            "#ff5050",
            "#960032",
            "#7d2181"
        )

        assertEquals(expected, getColorsResponse)
        mockServer.verify()
    }
}
