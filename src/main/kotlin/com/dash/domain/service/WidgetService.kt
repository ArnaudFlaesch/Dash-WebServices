package com.dash.domain.service

import com.dash.domain.mapping.WidgetMapper
import com.dash.domain.model.WidgetDomain
import com.dash.infra.repository.WidgetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WidgetService {

    @Autowired
    private lateinit var widgetRepository: WidgetRepository

    @Autowired
    private lateinit var widgetMapper: WidgetMapper

    fun findByTabIdOrderByWidgetOrderAsc(tabId: Int): List<WidgetDomain> =
        widgetRepository.findByTabIdOrderByWidgetOrderAsc(tabId).map(widgetMapper::mapWidgetEntityToWidgetDomain)

    fun getAllWidgets(): List<WidgetDomain> = widgetRepository.findAll().map(widgetMapper::mapWidgetEntityToWidgetDomain)

    fun addWidget(widgetType: Int, tabId: Int): WidgetDomain {
        val widgetOrder = widgetRepository.getNumberOfWidgetsByTab(tabId) + 1
        val widgetToInsert = WidgetDomain(0, tabId = tabId, type = widgetType, widgetOrder = widgetOrder, data = null)
        return saveWidget(widgetToInsert)
    }

    fun saveWidget(widget: WidgetDomain): WidgetDomain {
        val widgetToInsert = widgetMapper.mapWidgetDomainToWidgetEntity(widget)
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
