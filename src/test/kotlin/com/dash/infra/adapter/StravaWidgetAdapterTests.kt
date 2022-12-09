package com.dash.infra.adapter

import com.dash.infra.apimodel.strava.StravaActivityResponse
import com.dash.infra.rest.StravaApiClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class StravaWidgetAdapterTests {

    @Mock
    private lateinit var stravaApiClient: StravaApiClient

    @InjectMocks
    private lateinit var stravaWidgetAdapter: StravaWidgetAdapter

    @Test
    fun should_return_empty_strava_token_data() {
        val apiCode = "42195"
        given(stravaApiClient.getToken(apiCode)).willReturn(null)

        val response = stravaWidgetAdapter.getToken(apiCode)

        assertEquals("", response.accessToken)
        assertEquals("", response.refreshToken)
        assertEquals("", response.expiresAt)
        assertEquals("", response.athlete.username)
    }

    @Test
    fun should_return_empty_strava_refresh_token_data() {
        val refreshToken = "refresh_token"
        given(stravaApiClient.getRefreshToken(refreshToken)).willReturn(null)

        val response = stravaWidgetAdapter.getRefreshToken(refreshToken)

        assertEquals("", response.accessToken)
        assertEquals("", response.refreshToken)
        assertEquals("", response.expiresAt)
        assertEquals("", response.athlete.username)
    }

    @Test
    fun should_return_empty_strava_athlete_data() {
        val token = "token"
        given(stravaApiClient.getAthleteData(token)).willReturn(null)

        val response = stravaWidgetAdapter.getAthleteData(token)

        assertEquals(0, response.id)
        assertEquals("", response.username)
        assertEquals("", response.city)
    }

    @Test
    fun should_return_strava_activities_data() {
        val token = "token"
        val numberOfActivities = 25
        val activitiesDataList= listOf(StravaActivityResponse(name = "Evening run"), StravaActivityResponse(name = "Lunch run"))
        given(stravaApiClient.getAthleteActivities(token, numberOfActivities)).willReturn(activitiesDataList)

        val response = stravaWidgetAdapter.getAthleteActivities(token, numberOfActivities)

        assertEquals(2, response.size)
        assertEquals("Evening run", response[0].name)
        assertEquals("Lunch run", response[1].name)
    }

    @Test
    fun should_return_empty_strava_activities_data() {
        val token = "token"
        val numberOfActivities = 25
        given(stravaApiClient.getAthleteActivities(token, numberOfActivities)).willReturn(null)

        val response = stravaWidgetAdapter.getAthleteActivities(token, numberOfActivities)

        assertEquals(0, response.size)
    }
}
