package com.common.domain.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {
    @Autowired
    private lateinit var userService: UserService

    @Test
    fun getUserByUsernameTest() {
        val username = "admintest"
        val user = userService.getUserByUsername(username)
        assertThat(user).isNotEmpty
        assertEquals(username, user.get().username)
    }

    @Test
    @WithMockUser(username = "usertest", roles = ["USER"])
    fun shouldReturnUserWhenItExists() {
        val userId = userService.getCurrentAuthenticatedUserId()
        assertNotNull(userId)
    }
}
