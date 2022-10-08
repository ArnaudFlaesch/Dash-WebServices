package com.dash.app.controller

import com.dash.domain.service.RssWidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/rssWidget", produces = [ MediaType.APPLICATION_JSON_VALUE ])
class RssWidgetController {

    @Autowired
    private lateinit var rssWidgetService: RssWidgetService

    @GetMapping("/")
    fun getRssFeed(@RequestParam(value = "url") url: String): String =
        rssWidgetService.getJsonFeedFromUrl(url)
}
