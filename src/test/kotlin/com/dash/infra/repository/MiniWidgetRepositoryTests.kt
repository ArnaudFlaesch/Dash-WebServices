package com.dash.infra.repository

import com.common.infra.repository.UserRepository
import com.common.utils.SqlData
import com.dash.infra.entity.MiniWidgetEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SqlData
class MiniWidgetRepositoryTests {
    @Autowired
    private lateinit var miniWidgetRepository: MiniWidgetRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @AfterAll
    fun tearDown() {
        miniWidgetRepository.deleteAll()
    }

    @Test
    fun testInsertWidgets() {
        val authenticatedUser = userRepository.getReferenceById(1)
        val w1 = MiniWidgetEntity(0, 1, "{}", authenticatedUser)
        val w2 = MiniWidgetEntity(0, 1, "{}", authenticatedUser)
        miniWidgetRepository.saveAll(listOf(w1, w2))

        val listWidgets = miniWidgetRepository.findByUserId(authenticatedUser.id)
        assertThat(listWidgets).hasSize(2)
        assertNotNull(listWidgets[0].id)
        assertEquals(1, listWidgets[0].type)
        assertEquals(LinkedHashMap<String, String>(), listWidgets[0].data)
        assertEquals(authenticatedUser.id, listWidgets[0].user.id)
        assertEquals(1, listWidgets[1].type)
        assertEquals(LinkedHashMap<String, String>(), listWidgets[1].data)
        assertEquals(authenticatedUser.id, listWidgets[1].user.id)
    }
}
