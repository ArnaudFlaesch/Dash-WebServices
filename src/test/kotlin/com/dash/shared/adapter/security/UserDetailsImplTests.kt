package com.dash.shared.adapter.security

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.springframework.security.core.authority.SimpleGrantedAuthority

class UserDetailsImplTests {
    @Test
    fun shouldNotExposePasswordInToString() {
        val userDetails =
            UserDetailsImpl(
                id = 1,
                username = "usertest",
                email = "usertest@email.com",
                password = "\$2a\$10\$passwordHash",
                authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))
            )

        assertEquals("UserDetailsImpl(id=1, username=usertest, authorities=[ROLE_USER])", userDetails.toString())
        assertFalse(userDetails.toString().contains("passwordHash"))
    }
}
