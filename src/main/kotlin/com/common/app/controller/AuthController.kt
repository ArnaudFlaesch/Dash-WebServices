package com.common.app.controller

import com.common.app.controller.requests.LoginRequest
import com.common.app.security.JwtUtils
import com.common.app.security.UserDetailsImpl
import com.common.app.security.response.JwtResponse
import com.common.domain.event.DashEvent
import com.dash.domain.model.notification.NotificationType
import com.dash.domain.utils.Constants
import jakarta.validation.Valid
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils
) {
    @PostMapping("/login")
    fun authenticateUser(
        @Valid @RequestBody loginRequest: LoginRequest
    ): JwtResponse =
        authenticationManager
            .authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username, loginRequest.password)
            ).also { authentication ->
                SecurityContextHolder.getContext().authentication = authentication
                applicationEventPublisher.publishEvent(DashEvent(Constants.USER_LOGGED_IN_EVENT, NotificationType.WARN))
            }.let { authentication -> (authentication.principal as UserDetailsImpl).toJwtResponse() }

    private fun UserDetailsImpl.toJwtResponse(): JwtResponse =
        JwtResponse(
            accessToken = jwtUtils.generateJwtToken(username),
            id = id,
            username = username,
            email = email,
            roles = authorities.mapNotNull { it.authority }
        )
}
