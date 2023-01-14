package com.common.app.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/healthCheck")
class HealthCheckController {

    @GetMapping("/status")
    fun healthCheck(): ResponseEntity<*> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.TEXT_PLAIN)
            .body("Application is running.")
    }
}
