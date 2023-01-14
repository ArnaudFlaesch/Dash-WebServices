package com.common.app.controller

import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/healthCheck")
class HealthCheckController {

    companion object {
        private const val HEALTH_CHECK_MESSAGE = "Application is running."
    }

    @GetMapping("/status")
    fun healthCheck(): String {
        return HEALTH_CHECK_MESSAGE
    }
}
