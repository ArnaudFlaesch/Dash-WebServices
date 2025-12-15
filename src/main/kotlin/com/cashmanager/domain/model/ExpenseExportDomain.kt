package com.cashmanager.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExpenseExportDomain(
    @param:JsonProperty("id")
    val id: Int,
    @param:JsonProperty("amount")
    val amount: Float,
    @param:JsonProperty("expenseDate")
    val expenseDate: String,
    @param:JsonProperty("labelId")
    val labelId: Int
)
