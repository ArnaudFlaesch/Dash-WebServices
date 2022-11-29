package com.dash.infra.adapter

import com.dash.domain.mapping.AirParifWidgetMapper
import com.dash.infra.rest.AirParifApiClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class AirParifWidgetAdapterTests {

    @Mock
    private lateinit var airParifApiClient: AirParifApiClient

    @Mock
    private lateinit var airParifWidgetMapper: AirParifWidgetMapper

    @InjectMocks
    private lateinit var airParifWidgetAdapter: AirParifWidgetAdapter

    @Test
    fun should_return_empty_prevision_list_data() {
        val communeInseeCode = "75000"
        given(airParifApiClient.getPrevisionCommune(communeInseeCode)).willReturn(null)
        given(airParifWidgetMapper.previsionsResponseToDomain(communeInseeCode, LinkedHashMap())).willReturn(listOf())
        val response = airParifWidgetAdapter.getPrevisionCommune(communeInseeCode)
        assertEquals(0, response.size)
    }

    @Test
    fun should_return_empty_colors_data() {
        given(airParifApiClient.getColors()).willReturn(null)
        given(airParifWidgetMapper.colorsResponseToDomain(LinkedHashMap())).willReturn(listOf())
        val response = airParifWidgetAdapter.getColors()
        assertEquals(0, response.size)
    }
}
