package com.dash.dashboard.domain.model.widget

data class MiniWidget(
    val id: Int,
    val type: Int,
    val data: Any?,
    val userId: Int
)
