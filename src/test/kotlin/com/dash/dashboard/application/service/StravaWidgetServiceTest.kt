package com.dash.dashboard.application.service

import com.dash.dashboard.adapter.outbound.rest.NotFoundException
import com.dash.dashboard.adapter.outbound.rest.strava.StravaApi
import com.dash.dashboard.application.port.inbound.StravaWidgetUseCase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean

@SpringBootTest
class StravaWidgetServiceTest {
    @MockitoBean
    private lateinit var stravaApi: StravaApi

    @Autowired
    private lateinit var stravaWidgetService: StravaWidgetUseCase

    @Test
    fun shouldReturnEmptyStravaRefreshTokenData() {
        whenever(stravaApi.getToken(any(), any())).thenReturn(null)

        assertThrows<NotFoundException> {
            stravaWidgetService.getToken("apiCode")
        }
    }
}
