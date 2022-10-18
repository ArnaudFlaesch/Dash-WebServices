package com.dash.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TabDomain(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("label")
    val label: String,
    @JsonProperty("tabOrder")
    val tabOrder: Int,
    @JsonProperty("userId")
    val userId: Int
)
