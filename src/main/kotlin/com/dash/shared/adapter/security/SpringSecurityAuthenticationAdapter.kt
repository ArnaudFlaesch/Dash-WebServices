package com.dash.shared.adapter.security

import com.dash.shared.application.port.outbound.UserAuthenticationPort
import com.dash.shared.domain.model.AuthenticatedUser
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SpringSecurityAuthenticationAdapter(
    private val authenticationManager: AuthenticationManager
) : UserAuthenticationPort {
    /** Throws an AuthenticationException, answered with a 401 by Spring Security, if the credentials are wrong. */
    override fun authenticate(
        username: String,
        password: String
    ): AuthenticatedUser =
        authenticationManager
            .authenticate(UsernamePasswordAuthenticationToken.unauthenticated(username, password))
            .also { authentication -> SecurityContextHolder.getContext().authentication = authentication }
            .let { authentication -> (authentication.principal as UserDetailsImpl).toAuthenticatedUser() }
}
