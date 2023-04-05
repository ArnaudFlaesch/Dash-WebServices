package com.dash.infra.adapter

import com.common.infra.repository.UserRepository
import com.dash.domain.model.MiniWidgetDomain
import com.dash.infra.entity.MiniWidgetEntity
import com.dash.infra.repository.MiniWidgetRepository
import org.springframework.stereotype.Component

@Component
class MiniWidgetPersistenceAdapter(
    private val miniWidgetRepository: MiniWidgetRepository,
    private val userRepository: UserRepository
) {
    fun findAuthenticatedUserMiniWidgets(userId: Int): List<MiniWidgetDomain> =
        miniWidgetRepository.findByUserId(userId).map(MiniWidgetEntity::toDomain)

    fun saveMiniWidget(widgetData: MiniWidgetDomain): MiniWidgetDomain {
        val userEntity = userRepository.getReferenceById(widgetData.userId)
        val widgetToSave = MiniWidgetEntity(
            id = widgetData.id,
            type = widgetData.type,
            data = widgetData.data,
            user = userEntity
        )
        return miniWidgetRepository.save(widgetToSave).toDomain()
    }

    fun updateWidgetData(widgetId: Int, updatedData: Any): MiniWidgetDomain {
        val oldWidget = miniWidgetRepository.getReferenceById(widgetId)
        return miniWidgetRepository.save(oldWidget.copy(data = updatedData)).toDomain()
    }

    fun deleteMiniWidget(widgetId: Int) = miniWidgetRepository.deleteById(widgetId)
}
