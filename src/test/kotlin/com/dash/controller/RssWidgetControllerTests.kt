package com.dash.controller

import AbstractIT
import com.dash.utils.Constants.UNAUTHORIZED_ERROR
import com.dash.utils.IntegrationTestsUtils
import com.dash.utils.TestEndpointsArguments
import com.google.gson.Gson
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.http.Header
import io.restassured.parsing.Parser
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
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
import java.util.stream.Stream

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RssWidgetControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0

    private lateinit var mockServer: MockRestServiceServer

    @Autowired
    private lateinit var restTemplate: RestTemplate

    private lateinit var jwtToken: String

    private val rssWidgetEndpoint = "/rssWidget/"

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
    fun testGetUrl() {
        val url = "http://thelastpictureshow.over-blog.com/rss"

        val mockedResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\" xmlns:media=\"http://search.yahoo.com/mrss/\">\n" +
            "    <channel></channel>\n" +
            "</rss>"

        mockServer.expect(
            ExpectedCount.once(), requestTo(URI(url))
        )
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_XML)
                    .body(mockedResponse)
            )

        given()
            .port(port)
            .param("url", url)
            .contentType(ContentType.JSON)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`()
            .get(rssWidgetEndpoint)
            .then().log().all()
            .statusCode(200)
            .body(equalTo(Gson().toJson(mapOf("version" to "2.0", "channel" to ""))))
            .log().all()

        mockServer.verify()
    }

    @Test
    fun testGetUrlNullResponse() {
        val url = "http://thelastpictureshow.over-blog.com/rss"

        mockServer.expect(
            ExpectedCount.once(), requestTo(URI(url))
        )
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_XML)
            )

        given()
            .port(port)
            .param("url", url)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`()
            .get(rssWidgetEndpoint)
            .then().log().all()
            .statusCode(200)
            .body(equalTo(""))
            .log().all()

        mockServer.verify()
    }

    @ParameterizedTest
    @MethodSource("testGetUrlErrorCodes")
    fun testGetUrlErrorCodes(urlStatusCodeResponse: HttpStatus, expectedStatusCode: Int) {
        val url = "http://testwrongurl.com"

        mockServer.expect(
            ExpectedCount.once(), requestTo(URI(url))
        )
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(urlStatusCodeResponse)
                    .contentType(MediaType.APPLICATION_JSON)
            )

        given().port(port)
            .param("url", url)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`()
            .get(rssWidgetEndpoint)
            .then().log().all()
            .statusCode(expectedStatusCode)
            .log().all()
    }

    @Test
    fun testEndpointNotAuthenticated() {
        given().port(port)
            .param("url", "http://testwrongurl.com")
            .`when`()
            .get(rssWidgetEndpoint)
            .then().log().all()
            .statusCode(401)
            .log().all()
            .body("error", equalTo(UNAUTHORIZED_ERROR))
    }

    fun testGetUrlErrorCodes(): Stream<Arguments> = TestEndpointsArguments.testForeignApiCodes()
}
