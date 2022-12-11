package com.cashmanager.infra.repository

import com.common.utils.AbstractIT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ExpenseRepositoryTests : AbstractIT() {

    @Autowired
    private lateinit var expenseRepository: ExpenseRepository

    @Test
    fun testExpenses() {
        val expenseList = expenseRepository.findAll()
        assertEquals(4, expenseList.size)
        assertEquals(100.0f, expenseList[0].amount)
        assertEquals("Courses", expenseList[0].label.label)
    }
}
