package com.dash.dashboard.application.port.inbound

fun interface RssWidgetUseCase {
    /** Returns the RSS feed found at the URL, converted to JSON. */
    fun getJsonFeedFromUrl(url: String): String
}
