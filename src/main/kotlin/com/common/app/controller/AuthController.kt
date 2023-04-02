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
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/auth")
class AuthController(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils
) {

    @PostMapping("/login")
    fun authenticateUser(
        @Valid
        @RequestBody
        loginRequest: LoginRequest
    ): JwtResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)
        val userDetails = authentication.principal as UserDetailsImpl

        applicationEventPublisher.publishEvent(DashEvent(this, Constants.USER_LOGGED_IN_EVENT, NotificationType.WARN))
        val roles = userDetails.authorities.stream()
            .map { item: GrantedAuthority -> item.authority }
            .collect(Collectors.toList())

        return JwtResponse(jwt, userDetails.id, userDetails.username, userDetails.email, roles)
    }
}
