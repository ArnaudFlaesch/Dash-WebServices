package com.dash.domain.service

import com.dash.domain.model.calendarWidget.CalendarEvent
import com.dash.infra.rest.RestClient
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.Component
import net.fortuna.ical4j.model.component.VEvent
import org.springframework.stereotype.Service
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.time.temporal.Temporal

@Service
class CalendarWidgetService(
    private val proxyService: RestClient
) {
    fun getIcalDataFromUrl(url: String): List<CalendarEvent>? =
        proxyService
            .getDataFromProxy(url, String::class)
            .byteInputStream(StandardCharsets.ISO_8859_1)
            .let { stream -> InputStreamReader(stream, StandardCharsets.ISO_8859_1) }
            .let { reader ->
                val eventsList = mutableListOf<CalendarEvent>()
                CalendarBuilder().build(reader).getComponents<VEvent>(Component.VEVENT).forEach {
                    if (it.getDateTimeStart<Temporal>().isPresent && it.getDateTimeEnd<Temporal>().isPresent) {
                        eventsList.add(
                            CalendarEvent(
                                it.getDateTimeStart<Temporal>().get().date,
                                it.getDateTimeEnd<Temporal>().get().date,
                                it.summary.get().value
                            )
                        )
                    }
                }
                return eventsList
            }
}
