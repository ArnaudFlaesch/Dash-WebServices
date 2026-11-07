package com.dash.infra.repository

import com.dash.infra.entity.incidentwidget.IncidentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface IncidentWidgetRepository : JpaRepository<IncidentEntity, Int> {
    fun findByWidgetId(widgetId: Int): IncidentEntity?

    fun existsByIdAndWidgetTabUserId(
        id: Int,
        userId: Int
    ): Boolean

    @Modifying
    @Query("DELETE FROM IncidentEntity i WHERE i.widget.id IN :widgetIds")
    fun deleteByWidgetIds(widgetIds: Collection<Int>)
}
