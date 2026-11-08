package com.dash.dashboard.adapter.outbound.rest.strava

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class StravaAdapterTests {
    @Mock
    private lateinit var stravaApiClient: StravaApiClient

    @InjectMocks
    private lateinit var stravaAdapter: StravaAdapter

    @Test
    fun shouldReturnStravaToken() {
        val apiCode = "12"

        val stravaTokenResponse =
            StravaTokenDataResponse(accessToken = "Token")
        given(stravaApiClient.getToken(apiCode)).willReturn(stravaTokenResponse)

        val response = stravaAdapter.getToken(apiCode)
        assertEquals("Token", response.accessToken)
    }

    @Test
    fun shouldReturnStravaActivitiesData() {
        val token = "token"
        val pageNumber = 1
        val numberOfActivities = 25
        val activitiesDataList =
            listOf(
                StravaActivityResponse().copy(name = "Evening run"),
                StravaActivityResponse().copy(name = "Lunch run")
            )
        given(
            stravaApiClient.getAthleteActivities(token, pageNumber, numberOfActivities)
        ).willReturn(activitiesDataList)

        val response =
            stravaAdapter.getAthleteActivities(
                token,
                pageNumber,
                numberOfActivities
            )

        assertEquals(2, response.size)
        assertEquals("Evening run", response[0].name)
        assertEquals("Lunch run", response[1].name)
    }
}
