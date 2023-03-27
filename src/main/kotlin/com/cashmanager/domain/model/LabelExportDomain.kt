package com.cashmanager.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class LabelExportDomain(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("label")
    val label: String
)
