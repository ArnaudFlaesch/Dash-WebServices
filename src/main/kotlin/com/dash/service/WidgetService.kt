package com.dash.service

import com.dash.controller.requests.UpdateWidgetDataPayload
import com.dash.entity.Widget
import com.dash.repository.WidgetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WidgetService {
    @Autowired
    private lateinit var widgetRepository: WidgetRepository

    fun getAllWidgets(): List<Widget> = widgetRepository.findAll()

    fun addWidget(widget: Widget): Widget {
        val widgetOrder = widgetRepository.getNumberOfWidgetsByTab(widget.tab.id) + 1
        return widgetRepository.save(widget.copy(widgetOrder = widgetOrder))
    }

    fun updateWidget(widgetId: Int, updateWidgetDataPayload: UpdateWidgetDataPayload): Widget {
        val oldWidget = widgetRepository.getOne(widgetId)
        return widgetRepository.save(oldWidget.copy(data = updateWidgetDataPayload.data))
    }

    fun updateWidgetsOrder(widgets: List<Widget>): List<Widget> {
        return widgets.map { widget ->
            val oldWidget = widgetRepository.getOne(widget.id)
            return@map widgetRepository.save(oldWidget.copy(widgetOrder = widget.widgetOrder))
        }
    }

    fun findByTabIdOrderByWidgetOrderAsc(tabId: Int): List<Widget> = (widgetRepository.findByTabIdOrderByWidgetOrderAsc(tabId))

    fun deleteWidget(id: Int) = widgetRepository.deleteById(id)

    fun deleteWidgetsByTabId(id: Int) = widgetRepository.deleteWidgetsByTabId(id)
}
