package com.dash.domain.model.config

import com.fasterxml.jackson.annotation.JsonProperty

data class ImportWidget(
    @JsonProperty("type")
    val type: Int,

    @JsonProperty("data")
    val data: Any?,

    @JsonProperty("widgetOrder")
    val widgetOrder: Int,

    @JsonProperty("tabId")
    val tabId: Int
)
