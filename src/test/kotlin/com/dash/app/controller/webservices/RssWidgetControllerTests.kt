package com.dash.app.controller.webservices

import com.common.utils.Constants.UNAUTHORIZED_ERROR
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.google.gson.Gson
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.parsing.Parser
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.RestTemplate
import java.net.URI

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RssWidgetControllerTests {
    @LocalServerPort
    private val port: Int = 0

    @MockBean
    private lateinit var restTemplate: RestTemplate

    private lateinit var jwtToken: String

    private val rssWidgetEndpoint = "/rssWidget/"

    @BeforeAll
    fun setup() {
        RestAssured.defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
    }

    @Test
    fun testGetUrl() {
        val url = "http://thelastpictureshow.over-blog.com/rss"

        val mockedResponse =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\"" +
                " xmlns:media=\"http://search.yahoo.com/mrss/\">\n" +
                "    <channel></channel>\n" +
                "</rss>"

        Mockito
            .`when`(
                restTemplate.exchange(
                    URI.create(url),
                    HttpMethod.GET,
                    null,
                    String::class.java
                )
            ).thenReturn(ResponseEntity(mockedResponse, HttpStatus.OK))

        given()
            .port(port)
            .param("url", url)
            .contentType(ContentType.JSON)
            .header(createAuthenticationHeader(jwtToken))
            .`when`()
            .get(rssWidgetEndpoint)
            .then()
            .log()
            .all()
            .statusCode(200)
            .body(equalTo(Gson().toJson(mapOf("version" to "2.0", "channel" to ""))))
            .log()
            .all()
    }

    @Test
    fun testGetUrlNullResponse() {
        val url = "http://thelastpictureshow.over-blog.com/rss"

        Mockito
            .`when`(
                restTemplate.exchange(URI.create(url), HttpMethod.GET, null, String::class.java)
            ).thenReturn(ResponseEntity(HttpStatus.OK))

        given()
            .port(port)
            .param("url", url)
            .header(createAuthenticationHeader(jwtToken))
            .`when`()
            .get(rssWidgetEndpoint)
            .then()
            .log()
            .all()
            .statusCode(404)
    }

    @Test
    fun testEndpointNotAuthenticated() {
        given()
            .port(port)
            .param("url", "http://testwrongurl.com")
            .`when`()
            .get(rssWidgetEndpoint)
            .then()
            .log()
            .all()
            .statusCode(401)
            .log()
            .all()
            .body("error", equalTo(UNAUTHORIZED_ERROR))
    }
}
