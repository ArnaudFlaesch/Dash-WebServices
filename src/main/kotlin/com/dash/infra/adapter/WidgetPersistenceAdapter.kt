package com.dash.infra.adapter

import com.dash.domain.model.WidgetDomain
import com.dash.infra.entity.WidgetEntity
import com.dash.infra.repository.IncidentStreakRepository
import com.dash.infra.repository.IncidentWidgetRepository
import com.dash.infra.repository.TabRepository
import com.dash.infra.repository.WidgetRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class WidgetPersistenceAdapter(
    private val widgetRepository: WidgetRepository,
    private val tabRepository: TabRepository,
    private val incidentWidgetRepository: IncidentWidgetRepository,
    private val incidentStreakRepository: IncidentStreakRepository
) {
    fun findByTabIdOrderByWidgetOrderAsc(tabId: Int): List<WidgetDomain> =
        widgetRepository.findByTabIdOrderByWidgetOrderAsc(tabId).map(WidgetEntity::toDomain)

    fun getUserWidgets(userId: Int): List<WidgetDomain> =
        widgetRepository.getUsersWidget(userId).map(WidgetEntity::toDomain)

    fun getNumberOfWidgetsByTab(tabId: Int): Int = widgetRepository.getNumberOfWidgetsByTab(tabId)

    fun saveWidget(widgetData: WidgetDomain): WidgetDomain =
        WidgetEntity(
            id = widgetData.id,
            type = widgetData.type,
            data = widgetData.data,
            widgetOrder = widgetData.widgetOrder,
            tab = tabRepository.getReferenceById(widgetData.tabId)
        ).let(widgetRepository::save)
            .let(WidgetEntity::toDomain)

    @Transactional
    fun updateWidgetData(
        widgetId: Int,
        updatedData: Any
    ): WidgetDomain =
        widgetRepository
            .getReferenceById(widgetId)
            .apply { data = updatedData }
            .toDomain()

    @Transactional
    fun updateWidgetsOrder(widgetList: List<WidgetDomain>): List<WidgetDomain> =
        widgetList.map { widget ->
            widgetRepository
                .getReferenceById(widget.id)
                .apply { widgetOrder = widget.widgetOrder }
                .toDomain()
        }

    @Transactional
    fun deleteWidget(id: Int) {
        deleteWidgetsData(listOf(id))
        widgetRepository.deleteById(id)
    }

    /** Deletes the rows referencing the widgets (incident widget data), which would otherwise block their deletion. */
    @Transactional
    fun deleteWidgetsData(widgetIds: Collection<Int>) {
        widgetIds.takeIf(Collection<Int>::isNotEmpty)?.let { ids ->
            incidentStreakRepository.deleteByWidgetIds(ids)
            incidentWidgetRepository.deleteByWidgetIds(ids)
        }
    }
}
