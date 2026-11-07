package com.dash.shared.adapter.security

import com.dash.shared.adapter.outbound.persistence.user.UserRepository
import com.dash.shared.application.port.outbound.CurrentUserPort
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

/** Reads the current user from the Spring Security context, filled by AuthTokenFilter. */
@Component
class SecurityContextCurrentUserAdapter(
    private val userRepository: UserRepository
) : CurrentUserPort {
    override fun currentUsername(): String = currentAuthentication().name

    /** Uses the id of the authenticated principal, or looks the user up by name for other kinds of principals. */
    override fun currentUserId(): Int =
        (currentAuthentication().principal as? UserDetailsImpl)?.id
            ?: userRepository.findByUsername(currentUsername())?.id
            ?: throw NoSuchElementException("Authenticated user not found")

    private fun currentAuthentication(): Authentication =
        checkNotNull(SecurityContextHolder.getContext().authentication) { "No authenticated user" }
}
