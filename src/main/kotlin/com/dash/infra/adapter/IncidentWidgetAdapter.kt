package com.dash.infra.adapter

import com.dash.domain.model.incidentWidget.IncidentDomain
import com.dash.domain.model.incidentWidget.IncidentStreakDomain
import com.dash.infra.entity.incidentwidget.IncidentEntity
import com.dash.infra.entity.incidentwidget.IncidentStreakEntity
import com.dash.infra.repository.IncidentStreakRepository
import com.dash.infra.repository.IncidentWidgetRepository
import com.dash.infra.repository.WidgetRepository
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class IncidentWidgetAdapter(
    private val incidentWidgetRepository: IncidentWidgetRepository,
    private val incidentStreakRepository: IncidentStreakRepository,
    private val widgetRepository: WidgetRepository
) {
    fun getIncidentConfigForWidget(widgetId: Int): IncidentDomain =
        getIncidentConfigEntityForWidget(widgetId).toDomain()

    private fun getIncidentConfigEntityForWidget(widgetId: Int): IncidentEntity =
        incidentWidgetRepository.findByWidgetId(widgetId)
            ?: incidentWidgetRepository.save(
                IncidentEntity(0, OffsetDateTime.now(), widgetRepository.getReferenceById(widgetId))
            )

    fun startStreak(widgetId: Int): IncidentDomain =
        getIncidentConfigEntityForWidget(widgetId)
            .copy(lastIncidentDate = OffsetDateTime.now())
            .let(incidentWidgetRepository::save)
            .let(IncidentEntity::toDomain)

    fun endStreak(widgetId: Int): IncidentDomain {
        val incidentConfig = getIncidentConfigEntityForWidget(widgetId)
        val now = OffsetDateTime.now()
        incidentStreakRepository.save(IncidentStreakEntity(0, incidentConfig.lastIncidentDate, now, incidentConfig))
        return incidentWidgetRepository.save(incidentConfig.copy(lastIncidentDate = now)).toDomain()
    }

    fun getIncidentStreaks(incidentId: Int): List<IncidentStreakDomain> =
        incidentStreakRepository
            .findByIncidentId(incidentId)
            .map(IncidentStreakEntity::toDomain)
}
