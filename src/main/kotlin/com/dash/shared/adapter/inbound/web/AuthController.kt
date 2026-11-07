package com.dash.shared.adapter.inbound.web

import com.dash.shared.adapter.inbound.web.request.LoginRequest
import com.dash.shared.adapter.inbound.web.response.JwtResponse
import com.dash.shared.application.port.inbound.LoginUseCase
import com.dash.shared.domain.model.UserSession
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val loginUseCase: LoginUseCase
) {
    @PostMapping("/login")
    fun authenticateUser(
        @Valid @RequestBody loginRequest: LoginRequest
    ): JwtResponse = loginUseCase.login(loginRequest.username, loginRequest.password).toJwtResponse()

    private fun UserSession.toJwtResponse(): JwtResponse =
        JwtResponse(
            accessToken = accessToken,
            id = user.id,
            username = user.username,
            email = user.email,
            roles = user.roles
        )
}
