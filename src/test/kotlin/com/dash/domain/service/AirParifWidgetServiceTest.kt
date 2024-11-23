package com.dash.domain.service

import com.dash.domain.model.airParif.AirParifPrevisionEnum
import com.dash.domain.model.airParif.Prevision
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.net.URI

@SpringBootTest
class AirParifWidgetServiceTest {
    @MockBean
    private lateinit var restTemplate: RestTemplate

    @Autowired
    private lateinit var airParifWidgetService: AirParifWidgetService

    @Test
    fun testGetCustomResponse() {
        val mockedResponse = LinkedHashMap<String, List<LinkedHashMap<String, String>>>()
        val previsions = LinkedHashMap<String, String>()
        previsions["date"] = "2021-01-16"
        previsions["no2"] = "Bon"
        previsions["o3"] = "Mauvais"
        previsions["pm10"] = "Dégradé"
        previsions["pm25"] = "Moyen"
        previsions["so2"] = "bad format"
        previsions["indice"] = "Bon"
        previsions[""] = "Bon"
        mockedResponse["75112"] = listOf(previsions)

        Mockito
            .`when`(
                restTemplate.exchange(
                    any<URI>(),
                    any<HttpMethod>(),
                    any<HttpEntity<Any>>(),
                    any<ParameterizedTypeReference<Any>>()
                )
            ).thenReturn(ResponseEntity(mockedResponse, HttpStatus.OK))

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
