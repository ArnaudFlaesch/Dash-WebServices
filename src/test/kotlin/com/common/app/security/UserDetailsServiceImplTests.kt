package com.common.app.security

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UsernameNotFoundException

@SpringBootTest
class UserDetailsServiceImplTests {
    @Autowired
    private lateinit var userDetailsService: UserDetailsServiceImpl

    @Test
    fun shouldLoadExistingUser() {
        val userDetails = userDetailsService.loadUserByUsername("usertest")

        assertEquals("usertest", userDetails.username)
    }

    @Test
    fun shouldThrowWhenUserDoesNotExist() {
        val exception =
            assertThrows<UsernameNotFoundException> {
                userDetailsService.loadUserByUsername("unknownUser")
            }

        assertEquals("User Not Found with username: unknownUser", exception.message)
    }
}
