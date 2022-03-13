package com.dash.service

import com.dash.utils.XmlParserUtils.parseXmlDataToJsonString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RssWidgetService {

    @Autowired
    private lateinit var proxyService: ProxyService

    fun getJsonFeedFromUrl(url: String): String {
        val feedData = proxyService.getDataFromProxy(url)
        return if (feedData != null) {
            parseXmlDataToJsonString(feedData)
        } else {
            ""
        }
    }
}
