package com.dash.domain.service

import com.dash.infra.rest.NotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.restclient.test.autoconfigure.AutoConfigureMockRestServiceServer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@SpringBootTest
@AutoConfigureMockRestServiceServer
class RssWidgetServiceTest {
    @Autowired
    private lateinit var rssWidgetService: RssWidgetService

    @Autowired
    private lateinit var mockServer: MockRestServiceServer

    private val url = "http://thelastpictureshow.over-blog.com/rss"

    @Test
    fun testGetRequest() {
        val mockedResponse =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\"" +
                " xmlns:media=\"http://search.yahoo.com/mrss/\">\n" +
                "    <channel></channel>\n" +
                "</rss>"

        mockServer
            .expect(requestTo(url))
            .andRespond(withSuccess(mockedResponse, MediaType.APPLICATION_XML))

        val actualResponse = rssWidgetService.getJsonFeedFromUrl(url)
        assertEquals("{\"version\":\"2.0\",\"channel\":\"\"}", actualResponse)
    }

    @Test
    fun testGetRequestNullResponse() {
        mockServer
            .expect(requestTo(url))
            .andRespond(withSuccess())

        assertThrows<NotFoundException> {
            rssWidgetService.getJsonFeedFromUrl(url)
        }
    }
}
