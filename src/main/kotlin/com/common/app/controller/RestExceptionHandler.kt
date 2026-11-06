package com.common.app.controller

import jakarta.persistence.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.orm.ObjectRetrievalFailureException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestExceptionHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /** A request targeting an entity that doesn't exist is a client error, not a server failure. */
    @ExceptionHandler(EntityNotFoundException::class, ObjectRetrievalFailureException::class)
    fun handleEntityNotFound(exception: RuntimeException): ProblemDetail {
        logger.warn("Entity not found: {}", exception.message)
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Resource not found")
    }
}
