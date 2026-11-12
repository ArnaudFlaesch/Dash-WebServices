package com.dash.shared.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PageTests {
    private val items = (1..5).toList()

    @Test
    fun shouldReturnRequestedPage() {
        assertEquals(Page(listOf(3, 4), false, 5, 3, 2, 1), items.toPage(1, 2))
    }

    @Test
    fun shouldFlagLastPage() {
        assertEquals(Page(listOf(5), true, 5, 3, 1, 2), items.toPage(2, 2))
    }

    @Test
    fun shouldReturnFirstPageForNegativePageNumber() {
        assertEquals(Page(listOf(1, 2), false, 5, 3, 2, -1), items.toPage(-1, 2))
    }

    @Test
    fun shouldReturnEmptyPageBeyondLastPage() {
        assertEquals(Page(emptyList<Int>(), true, 5, 3, 0, 7), items.toPage(7, 2))
    }

    @Test
    fun shouldReturnEmptyLastPageForEmptyList() {
        assertEquals(Page(emptyList<Int>(), true, 0, 0, 0, 0), emptyList<Int>().toPage(0, 2))
    }
}
