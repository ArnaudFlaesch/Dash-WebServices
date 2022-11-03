package com.dash.infra.rest

import com.common.utils.AbstractIT
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.RestTemplate
import java.net.URI

@SpringBootTest
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestClientTest : AbstractIT() {

    @Autowired
    private lateinit var proxyService: RestClient

    @MockBean
    private lateinit var restTemplate: RestTemplate

    val testUrl = "http://url.com"

    @Test
    fun testGetRequest() {
        val response = "response"

        Mockito.`when`(restTemplate.exchange(URI.create(testUrl), HttpMethod.GET, null, String::class.java)).thenReturn(
            ResponseEntity(response, HttpStatus.OK)
        )

        val actualResponse = proxyService.getDataFromProxy(testUrl, String::class)
        assertEquals(response, actualResponse)
    }
}
