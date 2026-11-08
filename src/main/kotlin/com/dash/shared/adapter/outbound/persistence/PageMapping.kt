package com.dash.shared.adapter.outbound.persistence

import com.dash.shared.domain.model.Page

/** Converts a Spring Data page, so that it doesn't leak out of the persistence adapters. */
fun <T : Any, R> org.springframework.data.domain.Page<T>.toDomainPage(transform: (T) -> R): Page<R> =
    Page(
        content = content.map(transform),
        last = isLast,
        totalElements = totalElements,
        totalPages = totalPages,
        size = size,
        number = number
    )
