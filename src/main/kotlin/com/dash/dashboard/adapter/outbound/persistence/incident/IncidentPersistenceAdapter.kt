package com.dash.dashboard.adapter.outbound.persistence.incident

import com.dash.dashboard.adapter.outbound.persistence.widget.WidgetRepository
import com.dash.dashboard.application.port.outbound.IncidentPersistencePort
import com.dash.dashboard.domain.model.incident.Incident
import com.dash.dashboard.domain.model.incident.IncidentStreak
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Component
class IncidentPersistenceAdapter(
    private val incidentRepository: IncidentRepository,
    private val incidentStreakRepository: IncidentStreakRepository,
    private val widgetRepository: WidgetRepository
) : IncidentPersistencePort {
    @Transactional
    override fun getIncidentConfigForWidget(widgetId: Int): Incident = getOrCreateIncidentConfig(widgetId).toDomain()

    @Transactional
    override fun startStreak(widgetId: Int): Incident =
        getOrCreateIncidentConfig(widgetId)
            .apply { lastIncidentDate = OffsetDateTime.now() }
            .toDomain()

    @Transactional
    override fun endStreak(widgetId: Int): Incident =
        OffsetDateTime.now().let { now ->
            getOrCreateIncidentConfig(widgetId)
                .also { incident ->
                    incidentStreakRepository.save(IncidentStreakEntity(0, incident.lastIncidentDate, now, incident))
                }.apply { lastIncidentDate = now }
                .toDomain()
        }

    override fun getIncidentStreaks(incidentId: Int): List<IncidentStreak> =
        incidentStreakRepository
            .findByIncidentIdOrderByStreakStartDateAsc(incidentId)
            .map(IncidentStreakEntity::toDomain)

    private fun getOrCreateIncidentConfig(widgetId: Int): IncidentEntity =
        incidentRepository.findByWidgetId(widgetId)
            ?: incidentRepository.save(
                IncidentEntity(0, OffsetDateTime.now(), widgetRepository.getReferenceById(widgetId))
            )

    override fun isIncidentOwnedBy(
        incidentId: Int,
        userId: Int
    ): Boolean = incidentRepository.existsByIdAndWidgetTabUserId(incidentId, userId)
}
