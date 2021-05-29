package com.dash.repository

import com.dash.entity.Tab
import com.dash.entity.Widget
import com.dash.enums.RoleEnum
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@TabDataset
@ExtendWith(SpringExtension::class)
class UserRepositoryTests {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun testGetUsers() {
        val listUsers = userRepository.findAll()
        assertThat(listUsers).hasSize(2)
        assertThat(listUsers[0].role?.getName()).isEqualTo(RoleEnum.ROLE_USER)
        assertThat(listUsers[1].role?.getName()).isEqualTo(RoleEnum.ROLE_ADMIN)
    }

    @Test
    fun testGetUserByUsername() {
        val user = userRepository.findByUsername("usertest")
        if (user.isEmpty) {
            fail()
        } else {
            assertEquals("usertest", user.get().username)
            assertEquals(RoleEnum.ROLE_USER, user.get().role?.getName() ?: fail())
            assertEquals("user@email.com", user.get().email)
        }
    }
}
