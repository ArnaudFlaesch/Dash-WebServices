package com.dash.dashboard.adapter.outbound.rest.proxy

import com.dash.dashboard.application.port.outbound.CalendarPort
import com.dash.dashboard.domain.model.calendar.CalendarEvent
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.Component
import net.fortuna.ical4j.model.component.VEvent
import java.io.StringReader
import java.net.URI

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
            .mapNotNull(VEvent::toCalendarEvent)
}
