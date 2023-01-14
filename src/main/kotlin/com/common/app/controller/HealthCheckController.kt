package com.common.app.controller

import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/healthCheck")
class HealthCheckController {

    @GetMapping("/status")
    fun healthCheck(): String {
        return "Application is running."
    }
}
