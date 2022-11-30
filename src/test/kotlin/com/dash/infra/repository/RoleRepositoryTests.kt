package com.dash.infra.repository

import com.common.infra.repository.RoleRepository
import com.common.utils.AbstractIT
import com.dash.infra.entity.RoleEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
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
        val newRoleEntity = RoleEntity(0, "ROLE_MODERATOR")
        val insertedRole = roleRepository.save(newRoleEntity)

        val rolesFromDatabase = roleRepository.findAll()
        assertEquals(3, rolesFromDatabase.size)

        roleRepository.delete(insertedRole)
    }
}
