package com.common.infra.repository

import com.common.utils.AbstractIT
import com.dash.infra.entity.UserEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserRepositoryTests : AbstractIT() {

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
        val newUserEntity = UserEntity(id = 0, email = "test@email.com", username = "testusername", password = "testpassword", role = roleUser)

        val insertedUser = userRepository.save(newUserEntity)
        assertNotNull(insertedUser.id)
        val listUsers = userRepository.findAll()
        assertThat(listUsers).hasSize(3)
        userRepository.delete(insertedUser)
    }

    @Test
    fun testGetUserByUsername() {
        val user = userRepository.findByUsername("usertest")
        if (user.isEmpty) {
            fail()
        } else {
            assertNotNull(user.get().id)
            assertEquals("usertest", user.get().username)
            assertEquals("ROLE_USER", user.get().role.name)
            assertEquals("user@email.com", user.get().email)
        }
    }

    @Test
    fun testGetUserByUsernameWrongUsername() {
        val user = userRepository.findByUsername("usertestFail")
        assertTrue(user.isEmpty)
    }
}
