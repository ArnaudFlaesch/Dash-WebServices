package com.dash.domain.service

import com.common.utils.AbstractIT
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

@SpringBootTest
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RssWidgetServiceTest : AbstractIT() {

    @Autowired
    private lateinit var rssWidgetService: RssWidgetService

    @Mock
    private lateinit var restTemplate: RestTemplate

    @Test
    fun testGetRequest() {
        val url = "http://thelastpictureshow.over-blog.com/rss"

        val mockedResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\" xmlns:media=\"http://search.yahoo.com/mrss/\">\n" +
            "    <channel></channel>\n" +
            "</rss>"

        Mockito.`when`(restTemplate.getForEntity<String>(url)).thenReturn(
            ResponseEntity(mockedResponse, HttpStatus.OK)
        )

        val actualResponse = rssWidgetService.getJsonFeedFromUrl(url)
        assertEquals("{\"version\":\"2.0\",\"channel\":\"\"}", actualResponse)
    }

    @Test
    fun testGetRequestNullResponse() {
        val url = "http://thelastpictureshow.over-blog.com/rss"

        Mockito.`when`(restTemplate.getForEntity<String>(url)).thenReturn(
            ResponseEntity(HttpStatus.OK)
        )

        val actualResponse = rssWidgetService.getJsonFeedFromUrl(url)
        assertEquals("", actualResponse)
    }
}
