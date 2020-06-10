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
@ExtendWith(SpringExtension::class)
class WidgetRepositoryTests(@Autowired val widgetRepository: WidgetRepository) {

    @Test
    fun `basic entity checks`() {
        val p = Widget(1,2, null, null, null)
        widgetRepository.save(p)
        assertThat(widgetRepository.findAll()).hasSize(1)
    }
}