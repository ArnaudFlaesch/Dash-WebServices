package com.dash.infra.adapter

import com.dash.domain.model.notification.NotificationDomain
import com.dash.infra.entity.NotificationEntity
import com.dash.infra.repository.NotificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class NotificationAdapter(private val notificationRepository: NotificationRepository) {

    fun getNotifications(pageNumber: Int, pageSize: Int): Page<NotificationDomain> =
        notificationRepository.findAllByOrderByNotificationDateDesc(PageRequest.of(pageNumber, pageSize)).map(NotificationEntity::toDomain)

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

    fun markNotificationsAsRead(notificationIds: List<Int>): List<NotificationDomain> {
        val updatedNotificationEntities = notificationIds.map {
            notificationRepository.getReferenceById(it).copy(isRead = true)
        }
        return notificationRepository.saveAll(updatedNotificationEntities).map(NotificationEntity::toDomain)
    }
}
