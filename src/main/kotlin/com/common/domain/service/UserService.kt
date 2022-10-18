package com.common.domain.service

import com.common.domain.mapping.UserMapper
import com.common.domain.model.UserDomain
import com.common.infra.repository.UserRepository
import com.common.security.UserDetailsImpl
import com.dash.infra.entity.UserEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userMapper: UserMapper

    fun getCurrentAuthenticatedUserId(): Int {
        val authentication = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken
        val userDetails = authentication.principal as UserDetailsImpl
        return userDetails.id ?: 0
    }

    fun getCurrentAuthenticatedUser(): UserDomain {
        val userId = getCurrentAuthenticatedUserId()
        return userMapper.mapEntityToDomain(getUserById(userId))
    }

    fun getUserById(userId: Int): UserEntity = userRepository.getReferenceById(userId)
}
