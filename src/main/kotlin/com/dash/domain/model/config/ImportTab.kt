package com.dash.domain.model.config

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ImportTab(
    @JsonProperty("id")
    val id: Int,

    @JsonProperty("label")
    val label: String,

    @JsonProperty("tabOrder")
    val tabOrder: Int
)
