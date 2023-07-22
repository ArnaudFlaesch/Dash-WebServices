package com.dash.infra.repository

import com.dash.infra.entity.incidentwidget.IncidentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IncidentWidgetRepository : JpaRepository<IncidentEntity, Int> {

    fun findByWidgetId(widgetId: Int): IncidentEntity?
}
