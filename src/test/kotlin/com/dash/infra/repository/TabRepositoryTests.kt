package com.dash.infra.repository

import com.common.infra.repository.UserRepository
import com.common.utils.AbstractIT
import com.dash.infra.entity.TabEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TabRepositoryTests : AbstractIT() {

    @Autowired
    private lateinit var tabRepository: TabRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun testAddTab() {
        val user = userRepository.getReferenceById(1)
        val tabToInsert = TabEntity(0, "Onglet test", 3, user)

        val createdTab = tabRepository.save(tabToInsert)
        assertNotNull(createdTab.id)
        assertEquals("Onglet test", createdTab.label)
        assertEquals(1, createdTab.user.id)

        tabRepository.delete(createdTab)
    }
}
