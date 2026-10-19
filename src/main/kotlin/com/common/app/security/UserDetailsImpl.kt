package com.common.app.security

import com.common.infra.entity.UserEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserDetailsImpl(
    val id: Int,
    private val username: String,
    val email: String,
    @field:JsonIgnore private val password: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {
    companion object {
        private const val serialVersionUID = 1L

        fun build(userEntity: UserEntity) =
            UserDetailsImpl(
                id = userEntity.id,
                username = userEntity.username,
                email = userEntity.email,
                password = userEntity.password,
                authorities = listOf(SimpleGrantedAuthority(userEntity.role.name))
            )
    }

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun getPassword(): String = password

    override fun getUsername(): String = username
}
