package com.dash.infra.adapter

import com.dash.infra.rest.StravaApiClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired

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

}