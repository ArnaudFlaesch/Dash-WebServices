package com.common.infra.repository

import com.common.infra.entity.UserEntity
import com.common.utils.SqlData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertNotNull

@SpringBootTest
@SqlData
class UserRepositoryTests {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Test
    fun testGetUsers() {
        val listUsers = userRepository.findAll()
        assertThat(listUsers).hasSize(2)
        assertThat(listUsers[0].role.name).isEqualTo("ROLE_USER")
        assertThat(listUsers[1].role.name).isEqualTo("ROLE_ADMIN")
    }

    @Test
    fun testAddUser() {
        val roleUser = roleRepository.getReferenceById(1)
        val newUserEntity =
            UserEntity(
                id = 0,
                email = "test@email.com",
                username = "testusername",
                password = "testpassword",
                role = roleUser
            )

        val insertedUser = userRepository.save(newUserEntity)
        assertNotNull(insertedUser.id)
        val listUsers = userRepository.findAll()
        assertThat(listUsers).hasSize(3)
        userRepository.delete(insertedUser)
    }

    @Test
    fun testGetUserByUsername() {
        val user = assertNotNull(userRepository.findByUsername("usertest"))
        assertEquals("usertest", user.username)
        assertEquals("ROLE_USER", user.role.name)
        assertEquals("user@email.com", user.email)
    }

    @Test
    fun testGetUserByUsernameWrongUsername() {
        assertNull(userRepository.findByUsername("usertestFail"))
    }
}
