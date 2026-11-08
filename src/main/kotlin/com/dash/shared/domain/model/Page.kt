package com.dash.shared.domain.model

/** A page of results, as returned by the paginated use cases. */
data class Page<T>(
    val content: List<T>,
    val last: Boolean,
    val totalElements: Long,
    val totalPages: Int,
    val size: Int,
    val number: Int
)

/** Returns the requested page of the list, a negative page number being read as the first page. */
fun <T> List<T>.toPage(
    pageNumber: Int,
    pageSize: Int
): Page<T> =
    chunked(pageSize).let { pages ->
        val content = pages.getOrElse(pageNumber.coerceAtLeast(0)) { emptyList() }
        Page(
            content = content,
            last = pageNumber >= pages.size - 1,
            totalElements = size.toLong(),
            totalPages = pages.size,
            size = content.size,
            number = pageNumber
        )
    }
