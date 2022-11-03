package com.dash.domain.service

import com.dash.infra.rest.RestClient
import com.dash.utils.XmlParserUtils.parseXmlDataToJsonString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RssWidgetService {

    @Autowired
    private lateinit var restClient: RestClient

    fun getJsonFeedFromUrl(url: String): String {
        val feedData = restClient.getDataFromProxy(url, String::class)
        return if (feedData != null) {
            parseXmlDataToJsonString(feedData)
        } else {
            ""
        }
    }
}
