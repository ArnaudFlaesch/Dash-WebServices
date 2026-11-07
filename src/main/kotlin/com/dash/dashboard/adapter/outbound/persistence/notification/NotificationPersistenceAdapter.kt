package com.dash.dashboard.adapter.outbound.persistence.notification

import com.dash.dashboard.application.port.outbound.NotificationPersistencePort
import com.dash.dashboard.domain.model.notification.Notification
import com.dash.shared.adapter.outbound.persistence.toDomainPage
import com.dash.shared.domain.model.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class NotificationPersistenceAdapter(
    private val notificationRepository: NotificationRepository
) : NotificationPersistencePort {
    override fun getNotifications(
        pageNumber: Int,
        pageSize: Int
    ): Page<Notification> =
        notificationRepository
            .findAllByOrderByNotificationDateDesc(
                PageRequest.of(pageNumber, pageSize)
            ).toDomainPage(NotificationEntity::toDomain)

    override fun saveNotification(notification: Notification): Notification =
        NotificationEntity(
            id = notification.id,
            message = notification.message,
            notificationType = notification.notificationType,
            notificationDate = notification.notificationDate,
            isRead = notification.isRead
        ).let(notificationRepository::save)
            .let(NotificationEntity::toDomain)

    @Transactional
    override fun markNotificationsAsRead(notificationIds: List<Int>): List<Notification> =
        notificationRepository
            .findAllById(notificationIds)
            .onEach { it.isRead = true }
            .map(NotificationEntity::toDomain)
}
