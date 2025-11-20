package com.cashmanager.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class LabelExportDomain(
    @param:JsonProperty("id")
    val id: Int,
    @param:JsonProperty("label")
    val label: String
)
