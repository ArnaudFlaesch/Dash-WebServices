package com.dash.service

import com.dash.entity.Widget
import com.dash.repository.WidgetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WidgetService {
    @Autowired
    private lateinit var widgetRepository: WidgetRepository

    fun addWidget(widget: Widget): Widget {
        val widgetOrder = widgetRepository.getNumberOfWidgetsByTab(widget.tab?.id!!) + 1
        widget.widgetOrder = widgetOrder
        return widgetRepository.save(widget)
    }

    fun updateWidget(widget: Widget): Widget {
        val oldWidget = widgetRepository.getOne(widget.id)
        oldWidget.data = widget.data
        return widgetRepository.save(oldWidget)
    }

    fun updateWidgetsOrder(widgets: List<Widget>): List<Widget> {
        return widgets.map { widget ->
            val oldWidget = widgetRepository.getOne(widget.id)
            oldWidget.widgetOrder = widget.widgetOrder
            widgetRepository.save(oldWidget)
        }
    }

    fun findByTabIdOrderByWidgetOrderAsc(tabId: Int): List<Widget> {
        return (widgetRepository.findByTabIdOrderByWidgetOrderAsc(tabId))
    }

    fun deleteWidget(id: Int) {
        widgetRepository.deleteById(id)
    }

    fun deleteWidgetsByTabId(id: Int) {
        return widgetRepository.deleteWidgetsByTabId(id)
    }
}
