package com.dash.dashboard.adapter.outbound.persistence.incident

import com.dash.dashboard.adapter.outbound.persistence.widget.WidgetRepository
import com.dash.dashboard.application.port.outbound.IncidentPersistencePort
import com.dash.dashboard.domain.model.incident.Incident
import com.dash.dashboard.domain.model.incident.IncidentStreak
import org.springframework.stereotype.Component

@Component
class IncidentPersistenceAdapter(
    private val incidentRepository: IncidentRepository,
    private val incidentStreakRepository: IncidentStreakRepository,
    private val widgetRepository: WidgetRepository
) : IncidentPersistencePort {
    override fun findIncidentByWidgetId(widgetId: Int): Incident? =
        incidentRepository.findByWidgetId(widgetId)?.toDomain()

    override fun saveIncident(incident: Incident): Incident =
        IncidentEntity(
            id = incident.id,
            lastIncidentDate = incident.lastIncidentDate,
            widget = widgetRepository.getReferenceById(incident.widgetId)
        ).let(incidentRepository::save)
            .let(IncidentEntity::toDomain)

    override fun saveIncidentStreak(incidentStreak: IncidentStreak): IncidentStreak =
        IncidentStreakEntity(
            id = incidentStreak.id,
            streakStartDate = incidentStreak.streakStartDate,
            streakEndDate = incidentStreak.streakEndDate,
            incident = incidentRepository.getReferenceById(incidentStreak.incidentId)
        ).let(incidentStreakRepository::save)
            .let(IncidentStreakEntity::toDomain)

    override fun getIncidentStreaks(incidentId: Int): List<IncidentStreak> =
        incidentStreakRepository
            .findByIncidentIdOrderByStreakStartDateAsc(incidentId)
            .map(IncidentStreakEntity::toDomain)

    override fun isIncidentOwnedBy(
        incidentId: Int,
        userId: Int
    ): Boolean = incidentRepository.existsByIdAndWidgetTabUserId(incidentId, userId)
}
