package com.cashmanager.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExpenseExportDomain(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("amount")
    val amount: Float,
    @JsonProperty("expenseDate")
    val expenseDate: String,
    @JsonProperty("labelId")
    val labelId: Int
)
