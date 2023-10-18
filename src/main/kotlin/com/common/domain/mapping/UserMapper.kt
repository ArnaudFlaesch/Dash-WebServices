package com.common.domain.mapping

import com.common.domain.model.UserDomain
import com.common.infra.entity.UserEntity
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun mapEntityToDomain(userEntity: UserEntity): UserDomain =
        UserDomain(
            id = userEntity.id,
            username = userEntity.username,
            email = userEntity.email,
            roleId = userEntity.role.id
        )
}
