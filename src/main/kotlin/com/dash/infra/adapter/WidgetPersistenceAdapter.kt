package com.dash.infra.adapter

import com.dash.domain.model.WidgetDomain
import com.dash.infra.entity.WidgetEntity
import com.dash.infra.repository.TabRepository
import com.dash.infra.repository.WidgetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class WidgetPersistenceAdapter {

    @Autowired
    private lateinit var widgetRepository: WidgetRepository

    @Autowired
    private lateinit var tabRepository: TabRepository

    fun findByTabIdOrderByWidgetOrderAsc(tabId: Int): List<WidgetDomain> =
        widgetRepository.findByTabIdOrderByWidgetOrderAsc(tabId).map(WidgetEntity::toDomain)

    fun getAllWidgets(): List<WidgetDomain> = widgetRepository.findAll().map(WidgetEntity::toDomain)

    fun getNumberOfWidgetsByTab(tabId: Int) = widgetRepository.getNumberOfWidgetsByTab(tabId)

    fun saveWidget(widgetData: WidgetDomain): WidgetDomain {
        val tabEntity = tabRepository.getReferenceById(widgetData.tabId)
        val widgetToSave = WidgetEntity(
            id = widgetData.id,
            type = widgetData.type,
            data = widgetData.data,
            widgetOrder = widgetData.widgetOrder,
            tab = tabEntity
        )
        return widgetRepository.save(widgetToSave).toDomain()
    }

    fun updateWidgetData(widgetId: Int, updatedData: Any): WidgetDomain {
        val oldWidget = widgetRepository.getReferenceById(widgetId)
        return widgetRepository.save(oldWidget.copy(data = updatedData)).toDomain()
    }

    fun updateWidgetsOrder(widgetList: List<WidgetDomain>): List<WidgetDomain> {
        return widgetRepository.saveAll(
            widgetList.map { widget ->
                val oldWidget = widgetRepository.getReferenceById(widget.id)
                return@map oldWidget.copy(widgetOrder = widget.widgetOrder)
            }
        ).map(WidgetEntity::toDomain)
    }

    fun deleteWidget(id: Int) = widgetRepository.deleteById(id)
}
