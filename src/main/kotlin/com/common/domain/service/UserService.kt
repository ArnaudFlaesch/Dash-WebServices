package com.common.domain.service

import com.common.app.security.UserDetailsImpl
import com.common.infra.entity.UserEntity
import com.common.infra.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getCurrentAuthenticatedUserUsername(): String = currentAuthentication().name

    fun getCurrentAuthenticatedUserId(): Int =
        (currentAuthentication().principal as? UserDetailsImpl)?.id
            ?: getUserByUsername(getCurrentAuthenticatedUserUsername())?.id
            ?: throw NoSuchElementException("Authenticated user not found")

    fun getUserById(userId: Int): UserEntity = userRepository.getReferenceById(userId)

    fun getUserByUsername(username: String): UserEntity? = userRepository.findByUsername(username)

    private fun currentAuthentication(): Authentication =
        checkNotNull(SecurityContextHolder.getContext().authentication) { "No authenticated user" }
}
