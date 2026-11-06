package com.dash.domain.service

import com.dash.domain.model.calendarWidget.CalendarEvent
import com.dash.infra.rest.ProxyClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.core.ParameterizedTypeReference
import java.time.LocalDate

class CalendarWidgetServiceTest {
    private val proxyClient: ProxyClient = mock()
    private val calendarWidgetService = CalendarWidgetService(proxyClient)

    @Test
    fun shouldDefaultMissingSummaryAndSkipEventsWithoutDates() {
        val calendar =
            """
            BEGIN:VCALENDAR
            PRODID:-//Test//Test//EN
            VERSION:2.0
            BEGIN:VEVENT
            DTSTART;VALUE=DATE:20221101
            DTEND;VALUE=DATE:20221102
            DTSTAMP:20220319T185542Z
            UID:no-summary@test
            END:VEVENT
            BEGIN:VEVENT
            DTSTART;VALUE=DATE:20221225
            DTSTAMP:20220319T185542Z
            UID:no-end@test
            SUMMARY:No end date
            END:VEVENT
            BEGIN:VEVENT
            DTSTAMP:20220319T185542Z
            UID:no-start@test
            SUMMARY:No start date
            END:VEVENT
            END:VCALENDAR
            """.trimIndent().replace("\n", "\r\n")
        whenever(proxyClient.get(any(), any<ParameterizedTypeReference<String>>())).thenReturn(calendar)

        val events = calendarWidgetService.getIcalDataFromUrl("https://calendar.test/basic.ics")

        assertEquals(
            listOf(CalendarEvent(LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 2), "")),
            events
        )
    }
}
