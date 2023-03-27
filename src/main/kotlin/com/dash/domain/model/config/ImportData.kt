package com.dash.domain.model.config

data class ImportData(
    val widgets: List<ImportWidget> = listOf(),
    val tabs: List<ImportTab> = listOf()
)
