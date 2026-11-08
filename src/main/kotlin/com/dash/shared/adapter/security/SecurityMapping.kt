package com.dash.shared.adapter.security

import com.dash.shared.adapter.outbound.persistence.user.UserEntity
import com.dash.shared.domain.model.AuthenticatedUser
import org.springframework.security.core.authority.SimpleGrantedAuthority

fun UserEntity.toUserDetails(): UserDetailsImpl =
    UserDetailsImpl(
        id = id,
        username = username,
        email = email,
        password = password,
        authorities = listOf(SimpleGrantedAuthority(role.name))
    )

fun UserDetailsImpl.toAuthenticatedUser(): AuthenticatedUser =
    AuthenticatedUser(
        id = id,
        username = username,
        email = email,
        roles = authorities.mapNotNull { it.authority }
    )
