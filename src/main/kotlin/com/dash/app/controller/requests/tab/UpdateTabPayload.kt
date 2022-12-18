package com.dash.app.controller.requests.tab

data class UpdateTabPayload(
    val id: Int,
    val label: String,
    val tabOrder: Int
)
