package com.common.infra.repository

import com.dash.infra.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Int> {

    fun findByUsername(username: String): Optional<User>
}
