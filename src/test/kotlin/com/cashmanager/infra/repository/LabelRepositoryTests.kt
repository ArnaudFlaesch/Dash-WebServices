package com.cashmanager.infra.repository

import com.common.utils.AbstractIT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
class LabelRepositoryTests(
    private val labelRepository: LabelRepository
) : AbstractIT() {

    @Test
    fun testGetLabels() {
        val labelList = labelRepository.findAll()
        assertEquals(2, labelList.size)
    }
}
