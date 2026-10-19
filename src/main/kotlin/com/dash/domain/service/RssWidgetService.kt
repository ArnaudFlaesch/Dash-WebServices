package com.dash.domain.service

import com.dash.infra.rest.ProxyClient
import com.dash.infra.rest.typeReference
import com.dash.utils.XmlParserUtils.parseXmlDataToJsonString
import org.springframework.stereotype.Service
import java.net.URI

@Service
class RssWidgetService(
    private val proxyClient: ProxyClient
) {
    fun getJsonFeedFromUrl(url: String): String =
        parseXmlDataToJsonString(proxyClient.get(URI.create(url), typeReference<String>()))
}
