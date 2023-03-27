package com.cashmanager.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class LabelExportDomain(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("label")
    val label: String
)
