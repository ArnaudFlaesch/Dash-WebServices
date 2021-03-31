package com.dash.repository

import com.dash.entity.Tab
import com.dash.entity.Widget
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@TabDataset
@ExtendWith(SpringExtension::class)
class WidgetRepositoryTests {

    @Autowired
    private lateinit var widgetRepository: WidgetRepository

    @Test
    fun `basic entity checks`() {
        val w1 = Widget(1, 2, null, 1, Tab(10, null, null))
        val w2 = Widget(2, 3, null, 2, Tab(10, null, null))
        widgetRepository.save(w1)
        widgetRepository.save(w2)

        val listWidgets = widgetRepository.findByTabIdOrderByWidgetOrderAsc(10)
        assertThat(listWidgets).hasSize(2)
        assertThat(listWidgets[0].type).isEqualTo(2)
        assertThat(listWidgets[1].type).isEqualTo(3)
    }
}
