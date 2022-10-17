package com.dash.app.controller.requests

data class UpdateTabPayload(
    val id: Int,
    val label: String,
    val tabOrder: Int
)