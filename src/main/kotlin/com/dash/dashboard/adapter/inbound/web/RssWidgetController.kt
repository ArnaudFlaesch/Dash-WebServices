package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.application.port.inbound.RssWidgetUseCase
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rssWidget", produces = [MediaType.APPLICATION_JSON_VALUE])
class RssWidgetController(
    private val rssWidgetUseCase: RssWidgetUseCase
) {
    @GetMapping("/")
    fun getRssFeed(
        @RequestParam url: String
    ): String = rssWidgetUseCase.getJsonFeedFromUrl(url)
}
