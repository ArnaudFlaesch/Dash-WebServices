package com.cashmanager.infra.repository

import com.common.utils.AbstractIT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class ExpenseRepositoryTests : AbstractIT() {

    @Autowired
    private lateinit var expenseRepository: ExpenseRepository

    @Test
    fun testExpenses() {
        val expenseList = expenseRepository.findAll()
        assertEquals(4, expenseList.size)
        assertNotNull(expenseList[0].id)
        assertEquals(100.0f, expenseList[0].amount)
        assertEquals(LocalDate.parse("2022-04-01"), expenseList[0].expenseDate)
        assertEquals("Courses", expenseList[0].label.label)
    }
}
