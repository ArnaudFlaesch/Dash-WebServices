package com.dash.domain.service

import com.dash.infra.rest.RestClient
import com.dash.utils.XmlParserUtils.parseXmlDataToJsonString
import org.springframework.stereotype.Service

@Service
class RssWidgetService(
    private val restClient: RestClient
) {
    fun getJsonFeedFromUrl(url: String): String = parseXmlDataToJsonString(restClient.getDataFromProxy(url, String::class))
}
