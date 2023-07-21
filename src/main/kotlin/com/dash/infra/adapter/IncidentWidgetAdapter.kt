package com.dash.infra.adapter

import com.dash.domain.model.incidentWidget.IncidentDomain
import com.dash.domain.model.incidentWidget.IncidentStreakDomain
import com.dash.infra.entity.incidentwidget.IncidentStreakEntity
import com.dash.infra.repository.IncidentStreakRepository
import com.dash.infra.repository.IncidentWidgetRepository
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class IncidentWidgetAdapter(
    private val incidentWidgetRepository: IncidentWidgetRepository,
    private val incidentStreakRepository: IncidentStreakRepository
) {

    fun getIncidentConfigForWidget(widgetId: Int): IncidentDomain =
        incidentWidgetRepository.findByWidgetId(widgetId).toDomain()

    fun startStreak(widgetId: Int): IncidentDomain {
        val oldIncidentConfig = incidentWidgetRepository.findByWidgetId(widgetId)
        val updatedConfig = oldIncidentConfig.copy(lastIncidentDate = OffsetDateTime.now())
        return incidentWidgetRepository.save(updatedConfig).toDomain()
    }

    fun endStreak(widgetId: Int): IncidentDomain {
        val oldIncidentConfig = incidentWidgetRepository.findByWidgetId(widgetId)
        val lastIncidentDate = oldIncidentConfig.lastIncidentDate
        return if (lastIncidentDate != null) {
            val oldStreak = IncidentStreakEntity(0, lastIncidentDate, OffsetDateTime.now(), oldIncidentConfig)
            this.incidentStreakRepository.save(oldStreak)
            val updatedConfig = oldIncidentConfig.copy(lastIncidentDate = OffsetDateTime.now())
            incidentWidgetRepository.save(updatedConfig)
        } else {
            oldIncidentConfig
        }.toDomain()
    }

    fun getIncidentStreaks(incidentId: Int): List<IncidentStreakDomain> =
        incidentStreakRepository.findByIncidentId(incidentId).map(IncidentStreakEntity::toDomain)
}
