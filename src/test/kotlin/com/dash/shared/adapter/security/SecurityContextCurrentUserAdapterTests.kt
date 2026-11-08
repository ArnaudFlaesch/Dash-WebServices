package com.dash.shared.adapter.security

import com.dash.shared.adapter.outbound.persistence.user.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser

@SpringBootTest
class SecurityContextCurrentUserAdapterTests {
    @Autowired
    private lateinit var currentUserAdapter: SecurityContextCurrentUserAdapter

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    @WithMockUser(username = "usertest", roles = ["USER"])
    fun shouldReturnUserWhenItExists() {
        assertEquals("usertest", currentUserAdapter.currentUsername())
        assertEquals(userRepository.findByUsername("usertest")?.id, currentUserAdapter.currentUserId())
    }

    @Test
    @WithMockUser(username = "unknownUser", roles = ["USER"])
    fun shouldThrowWhenAuthenticatedUserDoesNotExist() {
        val exception = assertThrows<NoSuchElementException> { currentUserAdapter.currentUserId() }
        assertEquals("Authenticated user not found", exception.message)
    }

    @Test
    fun shouldThrowWhenNoUserIsAuthenticated() {
        val exception = assertThrows<IllegalStateException> { currentUserAdapter.currentUsername() }
        assertEquals("No authenticated user", exception.message)
    }
}
