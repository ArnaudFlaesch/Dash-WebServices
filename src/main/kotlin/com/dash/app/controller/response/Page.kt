package com.dash.app.controller.response

data class Page<T>(
    val content: List<T>,
    val last: Boolean,
    val totalElements: Long,
    val totalPages: Int,
    val size: Int,
    val number: Int
)