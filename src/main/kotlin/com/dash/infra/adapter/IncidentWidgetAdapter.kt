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
    fun getIncidentConfigForWidget(widgetId: Int): IncidentDomain = this.getIncidentConfigEntityForWidget(widgetId).toDomain()

    private fun getIncidentConfigEntityForWidget(widgetId: Int): IncidentEntity =
        incidentWidgetRepository.findByWidgetId(widgetId).let { incidentConfig ->
            return if (incidentConfig != null) {
                incidentConfig
            } else {
                val widgetConfig = widgetRepository.getReferenceById(widgetId)
                incidentWidgetRepository.save(
                    IncidentEntity(0, OffsetDateTime.now(), widgetConfig)
                )
            }
        }

    fun startStreak(widgetId: Int): IncidentDomain {
        val oldIncidentConfig = getIncidentConfigEntityForWidget(widgetId)
        val updatedConfig = oldIncidentConfig.copy(lastIncidentDate = OffsetDateTime.now())
        return incidentWidgetRepository.save(updatedConfig).toDomain()
    }

    fun endStreak(widgetId: Int): IncidentDomain {
        val oldIncidentConfig = getIncidentConfigEntityForWidget(widgetId)
        val lastIncidentDate = oldIncidentConfig.lastIncidentDate
        val oldStreak = IncidentStreakEntity(0, lastIncidentDate, OffsetDateTime.now(), oldIncidentConfig)
        this.incidentStreakRepository.save(oldStreak)
        val updatedConfig = oldIncidentConfig.copy(lastIncidentDate = OffsetDateTime.now())
        return incidentWidgetRepository.save(updatedConfig).toDomain()
    }

    fun getIncidentStreaks(incidentId: Int): List<IncidentStreakDomain> =
        incidentStreakRepository.findByIncidentId(incidentId).map(IncidentStreakEntity::toDomain)
}
