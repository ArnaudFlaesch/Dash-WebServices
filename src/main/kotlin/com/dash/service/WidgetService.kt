package com.dash.service

import com.dash.entity.Widget
import com.dash.repository.WidgetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class WidgetService {
    @Autowired
    private lateinit var widgetRepository: WidgetRepository

    fun getAllWidgets(): List<Widget> {
        return widgetRepository.findAll()
    }

    fun addWidget(widget: Widget): Widget {
        val widgetOrder = widgetRepository.getNumberOfWidgetsByTab(widget.tab?.id!!) + 1
        widget.widgetOrder = widgetOrder
        return widgetRepository.save(widget)
    }

    fun updateWidget(widget: Widget): Widget {
        val oldWidget = widget.id?.let { widgetRepository.getOne(it) }
        return if (oldWidget != null) {
            oldWidget.data = widget.data
            widgetRepository.save(oldWidget)
        } else {
            throw EntityNotFoundException()
        }
    }

    fun updateWidgetsOrder(widgets: List<Widget>): List<Widget> {
        return widgets.map { widget ->
            val oldWidget = widget.id?.let { widgetRepository.getOne(it) }
            return@map if (oldWidget != null) {
                oldWidget.widgetOrder = widget.widgetOrder
                widgetRepository.save(oldWidget)
            } else {
                throw EntityNotFoundException()
            }
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
