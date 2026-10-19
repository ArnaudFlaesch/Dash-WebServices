package com.dash.infra.repository

import com.dash.infra.entity.incidentwidget.IncidentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface IncidentWidgetRepository : JpaRepository<IncidentEntity, Int> {
    fun findByWidgetId(widgetId: Int): IncidentEntity?
}
