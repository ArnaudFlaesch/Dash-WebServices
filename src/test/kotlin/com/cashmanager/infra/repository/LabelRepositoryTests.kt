package com.cashmanager.infra.repository

import com.common.utils.AbstractIT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class LabelRepositoryTests : AbstractIT() {

    private val labelRepository: LabelRepository

    @Test
    fun testGetLabels() {
        val labelList = labelRepository.findAll()
        assertEquals(2, labelList.size)
    }
}
