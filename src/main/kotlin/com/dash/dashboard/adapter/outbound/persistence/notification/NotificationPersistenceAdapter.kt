package com.dash.dashboard.adapter.outbound.persistence.notification

import com.dash.dashboard.application.port.outbound.NotificationPersistencePort
import com.dash.dashboard.domain.model.notification.Notification
import com.dash.shared.adapter.outbound.persistence.toDomainPage
import com.dash.shared.domain.model.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

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

    override fun getNotificationsByIds(notificationIds: List<Int>): List<Notification> =
        notificationRepository.findAllById(notificationIds).map(NotificationEntity::toDomain)

    override fun saveNotification(notification: Notification): Notification =
        notificationRepository.save(notification.toEntity()).toDomain()

    override fun saveNotifications(notifications: List<Notification>): List<Notification> =
        notificationRepository
            .saveAll(notifications.map(Notification::toEntity))
            .map(NotificationEntity::toDomain)
}
