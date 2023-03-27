package com.dash.domain.model.config

import com.fasterxml.jackson.annotation.JsonProperty

data class ImportTab(
    @JsonProperty("id")
    val id: Int,

    @JsonProperty("label")
    val label: String,

    @JsonProperty("tabOrder")
    val tabOrder: Int
)
