package com.dash.dashboard.adapter.outbound.rest.proxy

import com.dash.dashboard.application.port.outbound.CalendarPort
import com.dash.dashboard.domain.model.calendar.CalendarEvent
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.Component
import net.fortuna.ical4j.model.component.VEvent
import java.io.StringReader
import java.net.URI
import java.time.temporal.Temporal

/** Reads the events of iCal calendars. */
@org.springframework.stereotype.Component
class IcalCalendarAdapter(
    private val proxyClient: ProxyClient
) : CalendarPort {
    override fun getCalendarEvents(calendarUrl: String): List<CalendarEvent> =
        proxyClient
            .get(URI.create(calendarUrl), typeReference<String>())
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
