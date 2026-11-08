package com.dash.dashboard.application.port.inbound

import com.dash.dashboard.domain.model.widget.MiniWidget

interface MiniWidgetUseCase {
    fun getUserMiniWidgets(): List<MiniWidget>

    fun addMiniWidget(widgetType: Int): MiniWidget

    fun updateWidgetData(
        widgetId: Int,
        updatedData: Any
    ): MiniWidget

    fun deleteMiniWidget(widgetId: Int)
}
