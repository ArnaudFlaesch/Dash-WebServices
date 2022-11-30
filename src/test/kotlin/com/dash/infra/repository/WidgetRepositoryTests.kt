package com.dash.infra.repository

import com.common.infra.repository.UserRepository
import com.common.utils.AbstractIT
import com.dash.infra.entity.TabEntity
import com.dash.infra.entity.WidgetEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WidgetRepositoryTests : AbstractIT() {

    @Autowired
    private lateinit var widgetRepository: WidgetRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun testInsertWidgets() {
        val tabIdToInsertInto = 1
        val w1 = WidgetEntity(0, 2, "{}", 1, TabEntity(tabIdToInsertInto, "", 1, user = userRepository.getReferenceById(1)))
        val w2 = WidgetEntity(0, 3, "{}", 2, TabEntity(tabIdToInsertInto, "", 2, user = userRepository.getReferenceById(1)))
        widgetRepository.save(w1)
        widgetRepository.save(w2)

        val listWidgets = widgetRepository.findByTabIdOrderByWidgetOrderAsc(tabIdToInsertInto)
        assertThat(listWidgets).hasSize(3)
        assertThat(listWidgets[0].type).isEqualTo(1)
        assertThat(listWidgets[1].type).isEqualTo(2)
    }
}
