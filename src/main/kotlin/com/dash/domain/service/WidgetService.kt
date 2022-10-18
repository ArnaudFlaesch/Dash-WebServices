package com.dash.domain.service

import com.dash.domain.mapping.WidgetMapper
import com.dash.domain.model.WidgetDomain
import com.dash.infra.entity.WidgetEntity
import com.dash.infra.repository.WidgetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WidgetService {

    @Autowired
    private lateinit var widgetRepository: WidgetRepository

    @Autowired
    private lateinit var widgetMapper: WidgetMapper

    @Autowired
    private lateinit var tabService: TabService

    fun findByTabIdOrderByWidgetOrderAsc(tabId: Int): List<WidgetDomain> =
        widgetRepository.findByTabIdOrderByWidgetOrderAsc(tabId).map(widgetMapper::mapWidgetEntityToWidgetDomain)

    fun getAllWidgets(): List<WidgetDomain> = widgetRepository.findAll().map(widgetMapper::mapWidgetEntityToWidgetDomain)

    fun addWidget(widgetType: Int, tabId: Int): WidgetDomain {
        val widgetOrder = widgetRepository.getNumberOfWidgetsByTab(tabId) + 1
        return saveWidget(WidgetDomain(id = 0, type = widgetType, widgetOrder = widgetOrder, tabId = tabId, data = null))
    }

    fun saveWidget(widget: WidgetDomain): WidgetDomain {
        val widgetTab = tabService.getTabById(widget.tabId)
        val widgetToInsert = WidgetEntity(id = widget.id, tab = widgetTab, type = widget.type, widgetOrder = widget.widgetOrder, data = widget.data)
        return widgetMapper.mapWidgetEntityToWidgetDomain(widgetRepository.save(widgetToInsert))
    }

    fun updateWidget(widgetId: Int, updatedData: Any): WidgetDomain {
        val oldWidget = widgetRepository.getReferenceById(widgetId)
        return widgetMapper.mapWidgetEntityToWidgetDomain(widgetRepository.save(oldWidget.copy(data = updatedData)))
    }

    fun updateWidgetsOrder(widgetEntities: List<WidgetDomain>): List<WidgetDomain> {
        return widgetRepository.saveAll(
            widgetEntities.map { widget ->
                val oldWidget = widgetRepository.getReferenceById(widget.id)
                return@map oldWidget.copy(widgetOrder = widget.widgetOrder)
            }
        ).map(widgetMapper::mapWidgetEntityToWidgetDomain)
    }

    fun deleteWidget(id: Int) = widgetRepository.deleteById(id)
}
