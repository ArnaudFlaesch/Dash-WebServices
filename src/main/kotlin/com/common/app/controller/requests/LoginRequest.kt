package com.common.app.controller.requests

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    val username: @NotBlank String,
    val password: @NotBlank String
)
