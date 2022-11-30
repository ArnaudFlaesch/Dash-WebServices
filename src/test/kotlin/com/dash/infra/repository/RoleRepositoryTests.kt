package com.dash.infra.repository

import com.common.infra.repository.RoleRepository
import com.common.utils.AbstractIT
import com.dash.infra.entity.RoleEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
class RoleRepositoryTests(private val roleRepository: RoleRepository) : AbstractIT() {

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
