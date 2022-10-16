package com.common.domain.service

import com.common.infra.repository.UserRepository
import com.dash.infra.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    fun getUserById(userId: Int): User = userRepository.getReferenceById(userId)
}
