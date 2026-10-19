package com.dash.domain.service

import com.dash.domain.model.airParif.AirParifPrevisionEnum
import com.dash.domain.model.airParif.Prevision
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.restclient.test.autoconfigure.AutoConfigureMockRestServiceServer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.header
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@SpringBootTest
@AutoConfigureMockRestServiceServer
class AirParifWidgetServiceTest {
    @Autowired
    private lateinit var mockServer: MockRestServiceServer

    @Autowired
    private lateinit var airParifWidgetService: AirParifWidgetService

    @Test
    fun testGetCustomResponse() {
        val mockedResponse =
            """
            {
              "75112": [
                {
                  "date": "2021-01-16",
                  "no2": "Bon",
                  "o3": "Mauvais",
                  "pm10": "Dégradé",
                  "pm25": "Moyen",
                  "so2": "bad format",
                  "indice": "Bon",
                  "": "Bon"
                }
              ]
            }
            """.trimIndent()

        mockServer
            .expect(requestTo("http://localhost:5000/airParif/commune?insee=75112"))
            .andExpect(header("X-Api-Key", "test"))
            .andRespond(withSuccess(mockedResponse, MediaType.APPLICATION_JSON))

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
