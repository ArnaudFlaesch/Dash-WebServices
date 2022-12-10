package com.common.domain.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserDomain(
    val id: Int,
    @NotBlank
    @Size(max = 20)
    val username: String,
    @NotBlank
    @Size(max = 50)
    @Email
    val email: String,
    val roleId: Int
)
