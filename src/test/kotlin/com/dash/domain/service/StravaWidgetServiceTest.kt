thépackage com.dash.domain.service

import com.common.utils.AbstractIT
import com.dash.app.controller.ErrorHandler
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.net.URI

@SpringBootTest
class StravaWidgetServiceTest : AbstractIT() {
    @MockBean
    private lateinit var restTemplate: RestTemplate

    @Autowired
    private lateinit var stravaWidgetService: StravaWidgetService

    @Test
    fun shouldReturnEmptyStravaRefreshTokenData() {
        Mockito.`when`(restTemplate.exchange(URI.create("url"), HttpMethod.POST, null, String::class.java))
            .thenReturn(ResponseEntity(HttpStatus.OK))

        assertThrows<ErrorHandler.Companion.NotFoundException> { stravaWidgetService.getToken("apiCode") }
    }
}
