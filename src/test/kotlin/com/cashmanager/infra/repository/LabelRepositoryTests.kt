package com.cashmanager.infra.repository

import com.common.utils.AbstractIT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class LabelRepositoryTests : AbstractIT() {

    @Autowired
    private lateinit var labelRepository: LabelRepository

    @Test
    fun testGetLabels() {
        val labelList = labelRepository.findAll()
        assertEquals(2, labelList.size)
    }
}
