package com.dash.repository

import com.dash.entity.Role
import com.dash.utils.AbstractIT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class RoleRepositoryTests : AbstractIT() {

    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Test
    fun testGetAllRoles() {
        val rolesFromDatabase = roleRepository.findAll()
        assertEquals(2, rolesFromDatabase.size)
        assertEquals(1, rolesFromDatabase[0].id)
        assertEquals(2, rolesFromDatabase[1].id)
        assertEquals("ROLE_USER", rolesFromDatabase[0].name)
        assertEquals("ROLE_ADMIN", rolesFromDatabase[1].name)
    }

    @Test
    fun testAddRole() {
        val newRole = Role(0, "ROLE_MODERATOR")
        val insertedRole = roleRepository.save(newRole)

        val rolesFromDatabase = roleRepository.findAll()
        assertEquals(3, rolesFromDatabase.size)

        roleRepository.delete(insertedRole)
    }
}
