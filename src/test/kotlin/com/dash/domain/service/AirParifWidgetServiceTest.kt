package com.dash.domain.service

import com.dash.domain.model.airParif.AirParifPrevisionEnum
import com.dash.domain.model.airParif.Prevision
import com.dash.infra.rest.api.AirParifApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean

@SpringBootTest
class AirParifWidgetServiceTest {
    @MockitoBean
    private lateinit var airParifApi: AirParifApi

    @Autowired
    private lateinit var airParifWidgetService: AirParifWidgetService

    @Test
    fun testGetCustomResponse() {
        val previsions =
            mapOf(
                "date" to "2021-01-16",
                "no2" to "Bon",
                "o3" to "Mauvais",
                "pm10" to "Dégradé",
                "pm25" to "Moyen",
                "so2" to "bad format",
                "indice" to "Bon",
                "" to "Bon"
            )
        whenever(airParifApi.getPrevisionCommune("75112")).thenReturn(mapOf("75112" to listOf(previsions)))

        val getPrevisionsDataResponse = airParifWidgetService.getPrevisionCommune("75112")

        val prevision =
            Prevision(
                "2021-01-16",
                AirParifPrevisionEnum.BON,
                AirParifPrevisionEnum.MAUVAIS,
                AirParifPrevisionEnum.DEGRADE,
                AirParifPrevisionEnum.MOYEN,
                AirParifPrevisionEnum.MISSING,
                AirParifPrevisionEnum.BON
            )
        assertEquals(listOf(prevision), getPrevisionsDataResponse)
    }
}
