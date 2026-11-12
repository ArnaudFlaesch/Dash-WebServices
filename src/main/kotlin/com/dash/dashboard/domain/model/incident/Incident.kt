package com.dash.dashboard.domain.model.incident

import java.time.OffsetDateTime

/** The incident tracker of a widget: the current streak without incident started at [lastIncidentDate]. */
data class Incident(
    val id: Int,
    val lastIncidentDate: OffsetDateTime,
    val widgetId: Int
) {
    /** The tracker with a streak starting at [date]. */
    fun restartedAt(date: OffsetDateTime): Incident = copy(lastIncidentDate = date)

    /** Ends the current streak at [date]: the streak to record, and the tracker with a new streak starting then. */
    fun endStreakAt(date: OffsetDateTime): Pair<IncidentStreak, Incident> =
        IncidentStreak.new(streakStartDate = lastIncidentDate, streakEndDate = date, incidentId = id) to
            restartedAt(date)

    companion object {
        /** A tracker not saved yet, its first streak starting at [date]. */
        fun newStartingAt(
            date: OffsetDateTime,
            widgetId: Int
        ): Incident = Incident(id = 0, lastIncidentDate = date, widgetId = widgetId)
    }
}
