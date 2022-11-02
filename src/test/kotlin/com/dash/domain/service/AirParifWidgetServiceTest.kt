package com.dash.domain.service

import com.common.utils.AbstractIT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AirParifWidgetServiceTest : AbstractIT() {

    @Autowired
    private lateinit var airParifWidgetService: AirParifWidgetService

    @Test
    fun testGetRequest() {
        val communeInseeCode = "75112"
        val actualResponse = airParifWidgetService.getPrevisionCommune(communeInseeCode)
        assertEquals(2, actualResponse.size)
    }
}
