package com.common.security

import com.common.infra.repository.UserRepository
import com.dash.infra.entity.UserEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl : UserDetailsService {

    private val userRepository: UserRepository

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity: UserEntity = userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("User Not Found with username: $username") }
        return UserDetailsImpl.build(userEntity)
    }
}
