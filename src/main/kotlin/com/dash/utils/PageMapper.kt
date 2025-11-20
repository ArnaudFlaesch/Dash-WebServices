package com.dash.utils

import org.springframework.data.domain.Page

object PageMapper {
    fun <T : Any> mapPageToPageResponse(page: Page<T>): com.dash.app.controller.response.Page<T> =
        com.dash.app.controller.response.Page(
            content = page.content,
            last = page.isLast,
            totalElements = page.totalElements,
            totalPages = page.totalPages,
            size = page.size,
            number = page.number
        )
}
