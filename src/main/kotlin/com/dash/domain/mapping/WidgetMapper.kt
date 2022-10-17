package com.dash.domain.mapping

import com.dash.domain.model.WidgetDomain
import com.dash.infra.entity.WidgetEntity
import org.springframework.stereotype.Component

@Component
class WidgetMapper {

    fun mapWidgetEntityToWidgetDomain(widgetEntity: WidgetEntity) = WidgetDomain(
        id = widgetEntity.id,
        type = widgetEntity.type,
        data = widgetEntity.data,
        widgetOrder = widgetEntity.widgetOrder,
        tabId = widgetEntity.tab.id
    )
}
