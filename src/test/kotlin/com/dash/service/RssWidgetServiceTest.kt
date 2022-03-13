package com.dash.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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

@SpringBootTest
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RssWidgetServiceTest {

    @Autowired
    private lateinit var rssWidgetService: RssWidgetService

    private lateinit var mockServer: MockRestServiceServer

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @BeforeAll
    fun setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate)
    }

    @Test
    fun testGetRequest() {
        val url = "http://thelastpictureshow.over-blog.com/rss"

        val mockedResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\" xmlns:media=\"http://search.yahoo.com/mrss/\">\n" +
            "    <channel></channel>\n" +
            "</rss>"

        mockServer.expect(ExpectedCount.once(), requestTo(URI(url)))
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_XML).body(mockedResponse)
            )
        val actualResponse = rssWidgetService.getJsonFeedFromUrl(url)
        assertEquals("{\"version\":\"2.0\",\"channel\":\"\"}", actualResponse)
        mockServer.verify()
    }

    @Test
    fun testGetRequestNullResponse() {
        val url = "http://thelastpictureshow.over-blog.com/rss"

        mockServer.expect(ExpectedCount.once(), requestTo(URI(url)))
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(HttpStatus.OK)
            )
        val actualResponse = rssWidgetService.getJsonFeedFromUrl(url)
        assertEquals("", actualResponse)
        mockServer.verify()
    }
}
