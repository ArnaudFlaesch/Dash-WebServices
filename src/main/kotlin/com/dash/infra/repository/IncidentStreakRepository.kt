package com.dash.infra.repository

import com.dash.infra.entity.incidentwidget.IncidentStreakEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface IncidentStreakRepository : JpaRepository<IncidentStreakEntity, Int> {
    fun findByIncidentIdOrderByStreakStartDateAsc(incidentId: Int): List<IncidentStreakEntity>

    @Modifying
    @Query(
        "DELETE FROM IncidentStreakEntity s WHERE s.incident.id IN " +
            "(SELECT i.id FROM IncidentEntity i WHERE i.widget.id IN :widgetIds)"
    )
    fun deleteByWidgetIds(widgetIds: Collection<Int>)
}
