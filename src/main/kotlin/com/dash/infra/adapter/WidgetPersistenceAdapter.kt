package com.dash.infra.adapter

import com.dash.domain.model.WidgetDomain
import com.dash.infra.entity.WidgetEntity
import com.dash.infra.repository.TabRepository
import com.dash.infra.repository.WidgetRepository
import org.springframework.stereotype.Component

@Component
class WidgetPersistenceAdapter(
    private val widgetRepository: WidgetRepository,
    private val tabRepository: TabRepository
) {
    fun findByTabIdOrderByWidgetOrderAsc(tabId: Int): List<WidgetDomain> = widgetRepository.findByTabIdOrderByWidgetOrderAsc(tabId).map(WidgetEntity::toDomain)

    fun getUserWidgets(userId: Int): List<WidgetDomain> = widgetRepository.getUsersWidget(userId).map(WidgetEntity::toDomain)

    fun getNumberOfWidgetsByTab(tabId: Int) = widgetRepository.getNumberOfWidgetsByTab(tabId)

    fun saveWidget(widgetData: WidgetDomain): WidgetDomain =
        WidgetEntity(
            id = widgetData.id,
            type = widgetData.type,
            data = widgetData.data,
            widgetOrder = widgetData.widgetOrder,
            tab = tabRepository.getReferenceById(widgetData.tabId)
        ).let(widgetRepository::save)
            .let(WidgetEntity::toDomain)

    fun updateWidgetData(
        widgetId: Int,
        updatedData: Any
    ): WidgetDomain =
        widgetRepository
            .getReferenceById(widgetId)
            .copy(data = updatedData)
            .let(widgetRepository::save)
            .let(WidgetEntity::toDomain)

    fun updateWidgetsOrder(widgetList: List<WidgetDomain>): List<WidgetDomain> {
        return widgetList
            .map { widget ->
                val oldWidget = widgetRepository.getReferenceById(widget.id)
                return@map oldWidget.copy(widgetOrder = widget.widgetOrder)
            }.let(widgetRepository::saveAll)
            .map(WidgetEntity::toDomain)
    }

    fun deleteWidget(id: Int) = widgetRepository.deleteById(id)
}
