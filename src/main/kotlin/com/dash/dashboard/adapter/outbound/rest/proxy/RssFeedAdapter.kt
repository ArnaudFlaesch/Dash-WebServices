package com.dash.dashboard.adapter.outbound.rest.proxy

import com.dash.dashboard.application.port.outbound.RssFeedPort
import org.springframework.stereotype.Component
import tools.jackson.databind.json.JsonMapper
import tools.jackson.dataformat.xml.XmlMapper
import java.net.URI

@Component
class RssFeedAdapter(
    private val proxyClient: ProxyClient
) : RssFeedPort {
    private val xmlMapper = XmlMapper()
    private val jsonMapper = JsonMapper()

    override fun getJsonFeed(url: String): String =
        proxyClient
            .get(URI.create(url), typeReference<String>())
            .let { xml -> jsonMapper.writeValueAsString(xmlMapper.readValue(xml, Any::class.java)) }
}
