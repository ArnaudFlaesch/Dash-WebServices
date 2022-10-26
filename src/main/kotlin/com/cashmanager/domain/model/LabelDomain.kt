package com.cashmanager.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class LabelDomain(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("label")
    val label: String,
    @JsonProperty("userId")
    val userId: Int
)
