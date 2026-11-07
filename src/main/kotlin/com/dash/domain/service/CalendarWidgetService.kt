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
            .mapNotNull(::toCalendarEvent)

    /** Events without a start or an end date can't be shown on a calendar, so they are skipped. */
    private fun toCalendarEvent(event: VEvent): CalendarEvent? =
        event.getDateTimeStart<Temporal>()?.let { start ->
            event.getDateTimeEnd<Temporal>()?.let { end ->
                CalendarEvent(start.date, end.date, event.summary?.value.orEmpty())
            }
        }
}
