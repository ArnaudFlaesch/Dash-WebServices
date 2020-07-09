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
        widgetRepository.getNumberOfWidgetsByTab(widget.tab?.id!!) + 1
        return widgetRepository.save(widget)
    }

    fun updateWidget(widget: Widget) : Widget {
        val oldWidget = widgetRepository.getOne(widget.id)
        oldWidget.data = widget.data
        return widgetRepository.save(oldWidget)
    }

    fun findByTabIdOrderByWidgetOrderAsc(tabId: Int): List<Widget> {
        return (widgetRepository.findByTabIdOrderByWidgetOrderAsc(tabId))
    }

    fun deleteWidget(id: Int) {
        val widget = widgetRepository.getOne(id)
        return widgetRepository.delete(widget)
    }

    fun deleteWidgetsByTabId(id: Int) {
        widgetRepository.deleteWidgetsByTabId(id)
    }
}