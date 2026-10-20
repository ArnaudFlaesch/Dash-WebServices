package com.dash.domain.service

import com.dash.infra.rest.NotFoundException
import com.dash.infra.rest.ProxyClient
import com.dash.infra.rest.typeReference
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import java.net.URI

@SpringBootTest
class RssWidgetServiceTest {
    @Autowired
    private lateinit var rssWidgetService: RssWidgetService

    @MockitoBean
    private lateinit var proxyClient: ProxyClient

    private val url = "http://thelastpictureshow.over-blog.com/rss"

    @Test
    fun testGetRequest() {
        val mockedResponse =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\"" +
                " xmlns:media=\"http://search.yahoo.com/mrss/\">\n" +
                "    <channel></channel>\n" +
                "</rss>"

        whenever(proxyClient.get(URI.create(url), typeReference<String>())).thenReturn(mockedResponse)

        val actualResponse = rssWidgetService.getJsonFeedFromUrl(url)
        assertEquals("{\"version\":\"2.0\",\"channel\":\"\"}", actualResponse)
    }

    @Test
    fun testGetRequestNullResponse() {
        whenever(proxyClient.get(URI.create(url), typeReference<String>())).thenThrow(NotFoundException())

        assertThrows<NotFoundException> {
            rssWidgetService.getJsonFeedFromUrl(url)
        }
    }
}
