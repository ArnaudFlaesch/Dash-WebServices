package com.dash.dashboard.adapter.outbound.rest.proxy

import com.dash.dashboard.domain.model.calendar.CalendarEvent
import net.fortuna.ical4j.model.component.VEvent
import java.time.temporal.Temporal

/** Events without a start or an end date can't be shown on a calendar, so they are mapped to null. */
fun VEvent.toCalendarEvent(): CalendarEvent? =
    getDateTimeStart<Temporal>()?.let { start ->
        getDateTimeEnd<Temporal>()?.let { end ->
            CalendarEvent(start.date, end.date, summary?.value.orEmpty())
        }
    }
