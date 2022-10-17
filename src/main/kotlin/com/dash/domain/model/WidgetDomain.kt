package com.dash.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class WidgetDomain(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("type")
    val type: Int,
    @JsonProperty("data")
    val data: Any?,
    @JsonProperty("widgetOrder")
    val widgetOrder: Int,
    @JsonProperty("tabId")
    val tabId: Int
)
