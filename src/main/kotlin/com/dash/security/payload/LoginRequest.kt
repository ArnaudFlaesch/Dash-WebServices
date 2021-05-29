package com.dash.security.payload

import javax.validation.constraints.NotBlank

class LoginRequest {
    var username: @NotBlank String? = null
    var password: @NotBlank String? = null

    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }
}
