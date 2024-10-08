package com.common.domain.service

import com.common.domain.mapping.UserMapper
import com.common.domain.model.UserDomain
import com.common.infra.entity.UserEntity
import com.common.infra.repository.UserRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {
    fun getCurrentAuthenticatedUserIdUsername(): String =
        (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken)
            .let { authentication -> authentication.principal as UserDetails }
            .username

    fun getCurrentAuthenticatedUserId(): Int =
        getCurrentAuthenticatedUserIdUsername()
            .let(this::getUserByUsername)
            .orElseThrow()
            .let(userMapper::mapEntityToDomain)
            .let(UserDomain::id)

    fun getUserById(userId: Int) = userRepository.getReferenceById(userId)

    fun getUserByUsername(username: String): Optional<UserEntity> = userRepository.findByUsername(username)
}
