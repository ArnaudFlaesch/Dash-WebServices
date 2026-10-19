package com.dash.domain.service

import com.dash.infra.rest.NotFoundException
import org.hamcrest.Matchers.startsWith
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.restclient.test.autoconfigure.AutoConfigureMockRestServiceServer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@SpringBootTest
@AutoConfigureMockRestServiceServer
class StravaWidgetServiceTest {
    @Autowired
    private lateinit var mockServer: MockRestServiceServer

    @Autowired
    private lateinit var stravaWidgetService: StravaWidgetService

    @Test
    fun shouldReturnEmptyStravaRefreshTokenData() {
        mockServer
            .expect(requestTo(startsWith("http://localhost:5000/strava/oauth/token")))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withSuccess())

        assertThrows<NotFoundException> {
            stravaWidgetService.getToken("apiCode")
        }
    }
}
