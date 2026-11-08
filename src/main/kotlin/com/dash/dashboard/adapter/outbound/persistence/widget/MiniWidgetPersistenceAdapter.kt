package com.dash.dashboard.adapter.outbound.persistence.widget

import com.dash.dashboard.application.port.outbound.MiniWidgetPersistencePort
import com.dash.dashboard.domain.model.widget.MiniWidget
import com.dash.shared.adapter.outbound.persistence.user.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MiniWidgetPersistenceAdapter(
    private val miniWidgetRepository: MiniWidgetRepository,
    private val userRepository: UserRepository
) : MiniWidgetPersistencePort {
    override fun getUserMiniWidgets(userId: Int): List<MiniWidget> =
        miniWidgetRepository.findByUserId(userId).map(MiniWidgetEntity::toDomain)

    override fun saveMiniWidget(miniWidget: MiniWidget): MiniWidget =
        MiniWidgetEntity(
            id = miniWidget.id,
            type = miniWidget.type,
            data = miniWidget.data,
            user = userRepository.getReferenceById(miniWidget.userId)
        ).let(miniWidgetRepository::save)
            .let(MiniWidgetEntity::toDomain)

    @Transactional
    override fun updateWidgetData(
        widgetId: Int,
        updatedData: Any
    ): MiniWidget =
        miniWidgetRepository
            .getReferenceById(widgetId)
            .apply { data = updatedData }
            .toDomain()

    override fun deleteMiniWidget(widgetId: Int) {
        miniWidgetRepository.deleteById(widgetId)
    }

    override fun isMiniWidgetOwnedBy(
        miniWidgetId: Int,
        userId: Int
    ): Boolean = miniWidgetRepository.existsByIdAndUserId(miniWidgetId, userId)
}
