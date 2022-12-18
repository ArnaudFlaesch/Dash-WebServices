package com.dash.infra.repository

import com.common.infra.repository.UserRepository
import com.common.utils.AbstractIT
import com.dash.infra.entity.MiniWidgetEntity
import org.assertj.core.api.Assertions.assertThat
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
        val w1 = MiniWidgetEntity(id = 0, type = 2, data = "{}", user = authenticatedUser)
        val w2 = MiniWidgetEntity(id = 0, type = 3, data = "{}", user = authenticatedUser)
        miniWidgetRepository.save(w1)
        miniWidgetRepository.save(w2)

        val listWidgets = miniWidgetRepository.findByUserId(authenticatedUser.id)
        assertThat(listWidgets).hasSize(2)
    }
}
