package com.dash.infra.adapter

import com.dash.domain.model.notification.NotificationDomain
import com.dash.infra.entity.NotificationEntity
import com.dash.infra.repository.NotificationRepository
import org.springframework.stereotype.Component

@Component
class NotificationAdapter(private val notificationRepository: NotificationRepository) {

    fun saveNotification(notification: NotificationDomain): NotificationDomain {
        val notificationEntity = NotificationEntity(
            id = notification.id,
            message = notification.message,
            notificationType = notification.notificationType.name,
            notificationDate = notification.notificationDate,
            isRead = notification.isRead
        )
        return notificationRepository.save(notificationEntity).toDomain()
    }
}
