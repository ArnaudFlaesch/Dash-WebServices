package com.dash.shared.adapter.inbound.web

import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.orm.ObjectRetrievalFailureException

class RestExceptionHandlerTests {
    private val restExceptionHandler = RestExceptionHandler()

    @Test
    fun shouldReturnNotFoundWhenEntityDoesNotExist() {
        val problemDetail =
            restExceptionHandler.handleEntityNotFound(EntityNotFoundException("Unable to find TabEntity with id 42"))

        assertEquals(HttpStatus.NOT_FOUND.value(), problemDetail.status)
        assertEquals("Resource not found", problemDetail.detail)
    }

    @Test
    fun shouldReturnNotFoundWhenObjectRetrievalFails() {
        val problemDetail = restExceptionHandler.handleEntityNotFound(ObjectRetrievalFailureException("TabEntity", 42))

        assertEquals(HttpStatus.NOT_FOUND.value(), problemDetail.status)
        assertEquals("Resource not found", problemDetail.detail)
    }
}
