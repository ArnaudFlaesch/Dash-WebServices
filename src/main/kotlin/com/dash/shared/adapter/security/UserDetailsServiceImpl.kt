package com.dash.shared.adapter.security

import com.dash.shared.adapter.outbound.persistence.user.UserEntity
import com.dash.shared.adapter.outbound.persistence.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {
    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails =
        userRepository
            .findByUsername(username)
            ?.let(UserEntity::toUserDetails)
            ?: throw UsernameNotFoundException("User Not Found with username: $username")
}
