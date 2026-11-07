package com.dash.infra.adapter

import com.dash.domain.model.notification.NotificationDomain
import com.dash.infra.entity.NotificationEntity
import com.dash.infra.repository.NotificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class NotificationAdapter(
    private val notificationRepository: NotificationRepository
) {
    fun getNotifications(
        pageNumber: Int,
        pageSize: Int
    ): Page<NotificationDomain> =
        notificationRepository
            .findAllByOrderByNotificationDateDesc(
                PageRequest.of(pageNumber, pageSize)
            ).map(NotificationEntity::toDomain)

    fun saveNotification(notification: NotificationDomain): NotificationDomain =
        NotificationEntity(
            id = notification.id,
            message = notification.message,
            notificationType = notification.notificationType,
            notificationDate = notification.notificationDate,
            isRead = notification.isRead
        ).let(notificationRepository::save)
            .let(NotificationEntity::toDomain)

    @Transactional
    fun markNotificationsAsRead(notificationIds: List<Int>): List<NotificationDomain> =
        notificationRepository
            .findAllById(notificationIds)
            .onEach { it.isRead = true }
            .map(NotificationEntity::toDomain)
}
