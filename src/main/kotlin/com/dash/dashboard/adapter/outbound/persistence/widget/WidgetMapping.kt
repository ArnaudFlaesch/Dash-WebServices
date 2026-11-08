package com.dash.dashboard.adapter.outbound.persistence.widget

import com.dash.dashboard.adapter.outbound.persistence.tab.TabEntity
import com.dash.dashboard.domain.model.widget.MiniWidget
import com.dash.dashboard.domain.model.widget.Widget
import com.dash.shared.adapter.outbound.persistence.user.UserEntity
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

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
