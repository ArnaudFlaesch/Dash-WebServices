package com.dash.app.controller.response

data class Page<T>(
    val content: List<T>,
    val last: Boolean,
    val totalElements: Long,
    val totalPages: Int,
    val size: Int,
    val number: Int
)

fun <T : Any> org.springframework.data.domain.Page<T>.toPageResponse(): Page<T> =
    Page(
        content = content,
        last = isLast,
        totalElements = totalElements,
        totalPages = totalPages,
        size = size,
        number = number
    )
