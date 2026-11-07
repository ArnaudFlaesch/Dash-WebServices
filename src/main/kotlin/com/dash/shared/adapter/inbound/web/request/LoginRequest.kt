package com.dash.shared.adapter.inbound.web.request

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String
)
