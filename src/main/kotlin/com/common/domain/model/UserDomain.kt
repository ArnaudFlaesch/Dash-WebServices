package com.common.domain.model

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

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
