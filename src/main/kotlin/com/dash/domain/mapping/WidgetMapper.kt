package com.dash.domain.mapping

import com.dash.domain.model.WidgetDomain
import com.dash.domain.service.TabService
import com.dash.infra.entity.WidgetEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class WidgetMapper {

    @Autowired
    private lateinit var tabService: TabService

    fun mapWidgetDomainToWidgetEntity(widgetDomain: WidgetDomain): WidgetEntity = WidgetEntity(
        id = widgetDomain.id,
        type = widgetDomain.type,
        data = widgetDomain.data,
        widgetOrder = widgetDomain.widgetOrder,
        tab = tabService.getTabById(widgetDomain.tabId)
    )

    fun mapWidgetEntityToWidgetDomain(widgetEntity: WidgetEntity) = WidgetDomain(
        id = widgetEntity.id,
        type = widgetEntity.type,
        data = widgetEntity.data,
        widgetOrder = widgetEntity.widgetOrder,
        tabId = widgetEntity.tab.id
    )
}
