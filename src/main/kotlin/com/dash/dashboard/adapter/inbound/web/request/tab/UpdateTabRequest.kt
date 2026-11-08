package com.dash.dashboard.adapter.inbound.web.request.tab

data class UpdateTabRequest(
    val id: Int,
    val label: String,
    val tabOrder: Int
)
