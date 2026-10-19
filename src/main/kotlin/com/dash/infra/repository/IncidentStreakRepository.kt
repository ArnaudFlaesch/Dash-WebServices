package com.dash.infra.repository

import com.dash.infra.entity.incidentwidget.IncidentStreakEntity
import org.springframework.data.jpa.repository.JpaRepository

interface IncidentStreakRepository : JpaRepository<IncidentStreakEntity, Int> {
    fun findByIncidentId(incidentId: Int): List<IncidentStreakEntity>
}
