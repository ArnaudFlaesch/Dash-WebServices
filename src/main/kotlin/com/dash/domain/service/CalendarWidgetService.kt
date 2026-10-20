package com.dash.domain.service

import com.dash.domain.model.calendarWidget.CalendarEvent
import com.dash.infra.rest.ProxyClient
import com.dash.infra.rest.typeReference
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.Component
import net.fortuna.ical4j.model.component.VEvent
import org.springframework.stereotype.Service
import java.io.StringReader
import java.net.URI
import java.time.temporal.Temporal

@Service
class CalendarWidgetService(
    private val proxyClient: ProxyClient
) {
    fun getIcalDataFromUrl(url: String): List<CalendarEvent> =
        proxyClient
            .get(URI.create(url), typeReference<String>())
            .let { CalendarBuilder().build(StringReader(it)) }
            .getComponents<VEvent>(Component.VEVENT)
            .mapNotNull { event ->
                val start = event.getDateTimeStart<Temporal>() ?: return@mapNotNull null
                val end = event.getDateTimeEnd<Temporal>() ?: return@mapNotNull null
                CalendarEvent(start.date, end.date, event.summary?.value.orEmpty())
            }
}
