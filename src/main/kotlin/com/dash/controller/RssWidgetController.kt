package com.dash.controller

import com.dash.service.ProxyService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/rssWidget")
class RssWidgetController {

    @Autowired
    private lateinit var proxyService: ProxyService

    @GetMapping("/")
    fun getRssFeed(@RequestParam(value = "url") url: String): Any? {
        return ObjectMapper().writeValueAsString(XmlMapper().readValue(proxyService.getDataFromProxy(url), Any::class.java))
    }
}
