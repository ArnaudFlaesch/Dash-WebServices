package com.dash.infra.repository

import com.common.infra.repository.UserRepository
import com.common.utils.AbstractIT
import com.dash.infra.entity.MiniWidgetEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MiniWidgetRepositoryTests : AbstractIT() {

    @Autowired
    private lateinit var miniWidgetRepository: MiniWidgetRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun testInsertWidgets() {
        val authenticatedUser = userRepository.getReferenceById(1)
        val w1 = MiniWidgetEntity(0, 1, "{}", authenticatedUser)
        val w2 = MiniWidgetEntity(0, 1, "{}", authenticatedUser)
        miniWidgetRepository.save(w1)
        miniWidgetRepository.save(w2)

        val listWidgets = miniWidgetRepository.findByUserId(authenticatedUser.id)
        assertThat(listWidgets).hasSize(2)
        assertEquals(1, listWidgets[0].type)
        assertEquals(1, listWidgets[1].type)
    }
}
