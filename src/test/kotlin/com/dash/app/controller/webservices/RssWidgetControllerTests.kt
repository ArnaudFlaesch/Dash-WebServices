package com.dash.app.controller.webservices

import com.common.utils.Constants.UNAUTHORIZED_ERROR
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.infra.rest.NotFoundException
import com.dash.infra.rest.ProxyClient
import com.dash.infra.rest.typeReference
import com.google.gson.Gson
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.parsing.Parser
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.net.URI

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RssWidgetControllerTests {
    @LocalServerPort
    private val port: Int = 0

    @MockitoBean
    private lateinit var proxyClient: ProxyClient

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

        whenever(proxyClient.get(URI.create(url), typeReference<String>())).thenReturn(mockedResponse)

        Given {
            port(port)
                .param("url", url)
                .contentType(ContentType.JSON)
                .header(createAuthenticationHeader(jwtToken))
        } When {
            get(rssWidgetEndpoint)
        } Then {
            log()
                .all()
                .statusCode(200)
                .body(equalTo(Gson().toJson(mapOf("version" to "2.0", "channel" to ""))))
                .log()
                .all()
        }
    }

    @Test
    fun testGetUrlNullResponse() {
        val url = "http://thelastpictureshow.over-blog.com/rss"

        whenever(proxyClient.get(URI.create(url), typeReference<String>())).thenThrow(NotFoundException())

        Given {
            port(port)
                .param("url", url)
                .header(createAuthenticationHeader(jwtToken))
        } When {
            get(rssWidgetEndpoint)
        } Then {
            log()
                .all()
                .statusCode(404)
        }
    }

    @Test
    fun testEndpointNotAuthenticated() {
        Given {
            port(port)
                .param("url", "http://testwrongurl.com")
        } When {
            get(rssWidgetEndpoint)
        } Then {
            log()
                .all()
                .statusCode(401)
                .log()
                .all()
                .body("error", equalTo(UNAUTHORIZED_ERROR))
        }
    }
}
