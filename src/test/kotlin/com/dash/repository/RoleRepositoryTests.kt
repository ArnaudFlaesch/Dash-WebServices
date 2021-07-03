package com.dash.repository

import com.dash.enums.RoleEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class RoleRepositoryTests {

    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Test
    fun testGetAllRoles() {
        val rolesFromDatabase = roleRepository.findAll()
        assertEquals(2, rolesFromDatabase.size)
        assertEquals(RoleEnum.ROLE_USER, rolesFromDatabase[0].name)
        assertEquals(RoleEnum.ROLE_ADMIN, rolesFromDatabase[1].name)
    }

}