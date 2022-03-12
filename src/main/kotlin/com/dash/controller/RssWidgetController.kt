package com.dash.controller

import com.dash.service.RssWidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/rssWidget")
class RssWidgetController {

    @Autowired
    private lateinit var rssWidgetService: RssWidgetService

    @GetMapping("/")
    fun getRssFeed(@RequestParam(value = "url") url: String): String =
        rssWidgetService.getUrlData(url)
}
