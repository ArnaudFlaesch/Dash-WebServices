package com.dash.utils

import org.springframework.data.domain.Page

object PageMapper {
    fun <T> mapPageToPageResponse(page: Page<T>): com.dash.app.controller.response.Page<T> {
        return com.dash.app.controller.response.Page(
            content = page.content,
            last = page.isLast,
            totalElements = page.totalElements,
            totalPages = page.totalPages,
            size = page.size,
            number = page.number,
        )
    }
}