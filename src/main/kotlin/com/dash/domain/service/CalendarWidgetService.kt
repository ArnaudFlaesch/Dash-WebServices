package com.dash.domain.service

import com.dash.domain.model.calendarWidget.CalendarEvent
import com.dash.infra.rest.RestClient
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.Component
import net.fortuna.ical4j.model.component.VEvent
import org.springframework.stereotype.Service
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.temporal.Temporal
import kotlin.jvm.optionals.getOrNull

@Service
class CalendarWidgetService(
    private val proxyService: RestClient
) {
    fun getIcalDataFromUrl(url: String): List<CalendarEvent>? =
        proxyService.getDataFromProxy(url, String::class).let { calendarData ->
            val stream = calendarData.byteInputStream(StandardCharsets.ISO_8859_1)
            val reader: Reader = InputStreamReader(stream, StandardCharsets.ISO_8859_1)
            CalendarBuilder().build(reader).getComponents<VEvent>(Component.VEVENT).map {
                CalendarEvent(
                    it.getDateTimeStart<Temporal>().getOrNull()?.date ?: Instant.now(),
                    it.getDateTimeEnd<Temporal>().getOrNull()?.date ?: Instant.now(),
                    it.summary.get().value
                )
            }
        }
}
