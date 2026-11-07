package com.dash.dashboard.application.service

import com.dash.dashboard.application.port.inbound.RssWidgetUseCase
import com.dash.dashboard.application.port.outbound.RssFeedPort
import org.springframework.stereotype.Service

@Service
class RssWidgetService(
    private val rssFeedPort: RssFeedPort
) : RssWidgetUseCase {
    override fun getJsonFeedFromUrl(url: String): String = rssFeedPort.getJsonFeed(url)
}
