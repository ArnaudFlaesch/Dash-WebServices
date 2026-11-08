package com.dash.shared.adapter.outbound.persistence.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Int> {
    fun findByUsername(username: String): UserEntity?
}
