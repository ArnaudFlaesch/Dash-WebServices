package com.dash.dashboard.application.port.outbound

fun interface RssFeedPort {
    /** Fetches the RSS feed found at the URL, converted to JSON. */
    fun getJsonFeed(url: String): String
}
