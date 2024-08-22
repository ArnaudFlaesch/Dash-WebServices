package com.cashmanager.infra.repository

import com.common.utils.SqlData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@SqlData
class LabelRepositoryTests {
    @Autowired
    private lateinit var labelRepository: LabelRepository

    @Test
    fun testGetLabels() {
        val labelList = labelRepository.findAll()
        assertEquals(2, labelList.size)
        assertNotNull(labelList[0].id)
        assertEquals("Courses", labelList[0].label)
        assertNotNull(labelList[0].user)
        assertNotNull(labelList[1].id)
        assertEquals("Restaurant", labelList[1].label)
        assertNotNull(labelList[1].user)
    }
}
