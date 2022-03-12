package com.dash.service

import com.dash.utils.XmlParserUtils.parseXmlData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RssWidgetService {

    @Autowired
    private lateinit var proxyService: ProxyService

    fun getUrlData(url: String): String =
        parseXmlData(proxyService.getDataFromProxy(url) ?: "")
}
