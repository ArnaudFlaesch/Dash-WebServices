package com.dash.domain.service

import com.dash.domain.model.WidgetDomain
import com.dash.infra.adapter.WidgetPersistenceAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WidgetService {

    @Autowired
    private lateinit var widgetPersistenceAdapter: WidgetPersistenceAdapter

    fun findByTabIdOrderByWidgetOrderAsc(tabId: Int): List<WidgetDomain> =
        widgetPersistenceAdapter.findByTabIdOrderByWidgetOrderAsc(tabId)

    fun getAllWidgets(): List<WidgetDomain> = widgetPersistenceAdapter.getAllWidgets()

    fun addWidget(widgetType: Int, tabId: Int): WidgetDomain {
        val widgetOrder = widgetPersistenceAdapter.getNumberOfWidgetsByTab(tabId) + 1
        return saveWidget(WidgetDomain(id = 0, type = widgetType, widgetOrder = widgetOrder, tabId = tabId, data = null))
    }

    fun saveWidget(widget: WidgetDomain): WidgetDomain = widgetPersistenceAdapter.saveWidget(widget)

    fun updateWidgetData(widgetId: Int, updatedData: Any): WidgetDomain =
        widgetPersistenceAdapter.updateWidgetData(widgetId, updatedData)

    fun updateWidgetsOrder(widgetList: List<WidgetDomain>): List<WidgetDomain> =
        widgetPersistenceAdapter.updateWidgetsOrder(widgetList)

    fun deleteWidget(id: Int) = widgetPersistenceAdapter.deleteWidget(id)
}
