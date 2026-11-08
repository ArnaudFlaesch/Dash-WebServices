package com.dash.dashboard.domain.model.tab

data class Tab(
    val id: Int,
    val label: String,
    val tabOrder: Int,
    val userId: Int
)
