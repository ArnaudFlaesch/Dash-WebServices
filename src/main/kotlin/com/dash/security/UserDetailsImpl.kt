package com.dash.security

import com.dash.entity.User
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
    val id: Long?,
    private val username: String?,
    val email: String?,
    @field:JsonIgnore private val password: String?,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    companion object {
        private const val serialVersionUID = 1L
        fun build(user: User): UserDetailsImpl {
            val authorities: List<GrantedAuthority> = listOf(SimpleGrantedAuthority(user.role.name.name))

            return UserDetailsImpl(
                user.id,
                user.username,
                user.email,
                user.password,
                authorities
            )
        }
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String? {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val user = other as UserDetailsImpl
        return id == user.id
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (username?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (password?.hashCode() ?: 0)
        result = 31 * result + authorities.hashCode()
        return result
    }
}
