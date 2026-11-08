package com.dash.dashboard.application.port.outbound

import com.dash.dashboard.domain.model.widget.MiniWidget

interface MiniWidgetPersistencePort {
    fun getUserMiniWidgets(userId: Int): List<MiniWidget>

    fun saveMiniWidget(miniWidget: MiniWidget): MiniWidget

    fun updateWidgetData(
        widgetId: Int,
        updatedData: Any
    ): MiniWidget

    fun deleteMiniWidget(widgetId: Int)

    fun isMiniWidgetOwnedBy(
        miniWidgetId: Int,
        userId: Int
    ): Boolean
}
