package com.dash.infra.adapter

import com.dash.domain.model.incidentWidget.IncidentDomain
import com.dash.domain.model.incidentWidget.IncidentStreakDomain
import com.dash.infra.entity.incidentwidget.IncidentEntity
import com.dash.infra.entity.incidentwidget.IncidentStreakEntity
import com.dash.infra.repository.IncidentStreakRepository
import com.dash.infra.repository.IncidentWidgetRepository
import com.dash.infra.repository.WidgetRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Component
class IncidentWidgetAdapter(
    private val incidentWidgetRepository: IncidentWidgetRepository,
    private val incidentStreakRepository: IncidentStreakRepository,
    private val widgetRepository: WidgetRepository
) {
    @Transactional
    fun getIncidentConfigForWidget(widgetId: Int): IncidentDomain = getOrCreateIncidentConfig(widgetId).toDomain()

    @Transactional
    fun startStreak(widgetId: Int): IncidentDomain =
        getOrCreateIncidentConfig(widgetId)
            .apply { lastIncidentDate = OffsetDateTime.now() }
            .toDomain()

    /** Records the current streak as ended now, and starts a new one. */
    @Transactional
    fun endStreak(widgetId: Int): IncidentDomain =
        OffsetDateTime.now().let { now ->
            getOrCreateIncidentConfig(widgetId)
                .also { incident ->
                    incidentStreakRepository.save(IncidentStreakEntity(0, incident.lastIncidentDate, now, incident))
                }.apply { lastIncidentDate = now }
                .toDomain()
        }

    fun getIncidentStreaks(incidentId: Int): List<IncidentStreakDomain> =
        incidentStreakRepository
            .findByIncidentIdOrderByStreakStartDateAsc(incidentId)
            .map(IncidentStreakEntity::toDomain)

    private fun getOrCreateIncidentConfig(widgetId: Int): IncidentEntity =
        incidentWidgetRepository.findByWidgetId(widgetId)
            ?: incidentWidgetRepository.save(
                IncidentEntity(0, OffsetDateTime.now(), widgetRepository.getReferenceById(widgetId))
            )
}
