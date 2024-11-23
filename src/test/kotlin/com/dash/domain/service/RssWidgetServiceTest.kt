package com.dash.domain.service

import com.dash.app.controller.ErrorHandler
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.net.URI

@SpringBootTest
class RssWidgetServiceTest {
    @Autowired
    private lateinit var rssWidgetService: RssWidgetService

    @MockBean
    private lateinit var restTemplate: RestTemplate

    @Test
    fun testGetRequest() {
        val url = "http://thelastpictureshow.over-blog.com/rss"

        val mockedResponse =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\"" +
                " xmlns:media=\"http://search.yahoo.com/mrss/\">\n" +
                "    <channel></channel>\n" +
                "</rss>"

        Mockito
            .`when`(
                restTemplate.exchange(URI.create(url), HttpMethod.GET, null, String::class.java)
            ).thenReturn(ResponseEntity(mockedResponse, HttpStatus.OK))

        val actualResponse = rssWidgetService.getJsonFeedFromUrl(url)
        assertEquals("{\"version\":\"2.0\",\"channel\":\"\"}", actualResponse)
    }

    @Test
    fun testGetRequestNullResponse() {
        val url = "http://thelastpictureshow.over-blog.com/rss"

        Mockito
            .`when`(
                restTemplate.exchange(URI.create(url), HttpMethod.GET, null, String::class.java)
            ).thenReturn(ResponseEntity(HttpStatus.OK))

        assertThrows<ErrorHandler.Companion.NotFoundException> {
            rssWidgetService
                .getJsonFeedFromUrl(
                    url
                )
        }
    }
}
