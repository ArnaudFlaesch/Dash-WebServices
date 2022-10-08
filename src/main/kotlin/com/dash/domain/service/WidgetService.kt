package com.dash.domain.service

import com.dash.app.controller.requests.UpdateWidgetDataPayload
import com.dash.infra.entity.Widget
import com.dash.infra.repository.WidgetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WidgetService {
    @Autowired
    private lateinit var widgetRepository: WidgetRepository

    fun getAllWidgets(): List<Widget> = widgetRepository.findAll()

    fun addWidget(widget: Widget): Widget {
        val widgetOrder = widgetRepository.getNumberOfWidgetsByTab(widget.tab.id) + 1
        return saveWidget(widget.copy(widgetOrder = widgetOrder))
    }

    fun saveWidget(widget: Widget): Widget {
        return widgetRepository.save(widget)
    }

    fun updateWidget(widgetId: Int, updateWidgetDataPayload: UpdateWidgetDataPayload): Widget {
        val oldWidget = widgetRepository.getReferenceById(widgetId)
        return widgetRepository.save(oldWidget.copy(data = updateWidgetDataPayload.data))
    }

    fun updateWidgetsOrder(widgets: List<Widget>): List<Widget> {
        return widgetRepository.saveAll(
            widgets.map { widget ->
                val oldWidget = widgetRepository.getReferenceById(widget.id)
                return@map oldWidget.copy(widgetOrder = widget.widgetOrder)
            }
        )
    }

    fun findByTabIdOrderByWidgetOrderAsc(tabId: Int): List<Widget> = widgetRepository.findByTabIdOrderByWidgetOrderAsc(tabId)

    fun deleteWidget(id: Int) = widgetRepository.deleteById(id)

    fun deleteWidgetsByTabId(id: Int) = widgetRepository.deleteWidgetsByTabId(id)
}
