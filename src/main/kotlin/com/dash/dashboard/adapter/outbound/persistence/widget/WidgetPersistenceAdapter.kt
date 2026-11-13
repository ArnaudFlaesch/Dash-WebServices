package com.dash.dashboard.adapter.outbound.persistence.widget

import com.dash.dashboard.adapter.outbound.persistence.incident.IncidentRepository
import com.dash.dashboard.adapter.outbound.persistence.incident.IncidentStreakRepository
import com.dash.dashboard.adapter.outbound.persistence.tab.TabRepository
import com.dash.dashboard.application.port.outbound.WidgetPersistencePort
import com.dash.dashboard.domain.model.widget.Widget
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class WidgetPersistenceAdapter(
    private val widgetRepository: WidgetRepository,
    private val tabRepository: TabRepository,
    private val incidentRepository: IncidentRepository,
    private val incidentStreakRepository: IncidentStreakRepository
) : WidgetPersistencePort {
    override fun getTabWidgets(tabId: Int): List<Widget> =
        widgetRepository.findByTabIdOrderByWidgetOrderAsc(tabId).map(WidgetEntity::toDomain)

    override fun getUserWidgets(userId: Int): List<Widget> =
        widgetRepository.getUsersWidget(userId).map(WidgetEntity::toDomain)

    override fun getNumberOfWidgetsByTab(tabId: Int): Int = widgetRepository.getNumberOfWidgetsByTab(tabId)

    override fun saveWidget(widget: Widget): Widget =
        WidgetEntity(
            id = widget.id,
            type = widget.type,
            data = widget.data,
            widgetOrder = widget.widgetOrder,
            tab = tabRepository.getReferenceById(widget.tabId)
        ).let(widgetRepository::save)
            .let(WidgetEntity::toDomain)

    @Transactional
    override fun updateWidgetData(
        widgetId: Int,
        updatedData: Any
    ): Widget =
        widgetRepository
            .getReferenceById(widgetId)
            .apply { data = updatedData }
            .toDomain()

    @Transactional
    override fun updateWidgetsOrder(widgetList: List<Widget>): List<Widget> =
        widgetList.map { widget ->
            widgetRepository
                .getReferenceById(widget.id)
                .apply { widgetOrder = widget.widgetOrder }
                .toDomain()
        }

    @Transactional
    override fun deleteWidget(widgetId: Int) {
        deleteWidgetsData(listOf(widgetId))
        widgetRepository.deleteById(widgetId)
    }

    @Transactional
    override fun deleteTabWidgets(tabId: Int) {
        widgetRepository
            .findByTabIdOrderByWidgetOrderAsc(tabId)
            .map(WidgetEntity::id)
            .let(::deleteWidgetsData)
        widgetRepository.deleteWidgetsByTabId(tabId)
    }

    override fun isWidgetOwnedBy(
        widgetId: Int,
        userId: Int
    ): Boolean = widgetRepository.existsByIdAndTabUserId(widgetId, userId)

    override fun findWidgetIdsOwnedBy(
        widgetIds: Collection<Int>,
        userId: Int
    ): Collection<Int> = widgetRepository.findIdsByIdInAndTabUserId(widgetIds, userId)

    /** Deletes the rows referencing the widgets (incident widget data), which would otherwise block their deletion. */
    private fun deleteWidgetsData(widgetIds: Collection<Int>) {
        widgetIds.takeIf(Collection<Int>::isNotEmpty)?.let { ids ->
            incidentStreakRepository.deleteByWidgetIds(ids)
            incidentRepository.deleteByWidgetIds(ids)
        }
    }
}
