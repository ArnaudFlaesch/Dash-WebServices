package com.dash.dashboard.adapter.outbound.persistence.widget

import com.dash.dashboard.domain.model.widget.MiniWidget
import com.dash.dashboard.domain.model.widget.Widget

fun WidgetEntity.toDomain(): Widget =
    Widget(
        id = this.id,
        type = this.type,
        data = this.data,
        widgetOrder = this.widgetOrder,
        tabId = this.tab.id
    )

fun MiniWidgetEntity.toDomain(): MiniWidget =
    MiniWidget(
        id = this.id,
        type = this.type,
        data = this.data,
        userId = this.user.id
    )
