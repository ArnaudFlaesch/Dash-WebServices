package com.dash.domain.service

import com.common.app.security.SecurityConditions
import com.common.domain.service.UserService
import com.dash.domain.model.notification.NotificationDomain
import com.dash.domain.model.notification.NotificationType
import com.dash.infra.adapter.NotificationAdapter
import org.springframework.data.domain.Page
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class NotificationService(
    private val userService: UserService,
    private val notificationAdapter: NotificationAdapter
) {
    @PreAuthorize(SecurityConditions.IS_USER_ADMIN)
    fun getNotifications(
        pageNumber: Int,
        pageSize: Int
    ): Page<NotificationDomain> = notificationAdapter.getNotifications(pageNumber, pageSize)

    fun saveNotification(
        message: String,
        notificationType: NotificationType
    ) {
        val userName = userService.getCurrentAuthenticatedUserUsername()
        val notification = createNotification("$userName : $message", notificationType)
        notificationAdapter.saveNotification(notification)
    }

    @PreAuthorize(SecurityConditions.IS_USER_ADMIN)
    fun markNotificationsAsRead(
        notificationIds: List<Int>
    ): List<NotificationDomain> = notificationAdapter.markNotificationsAsRead(notificationIds)

    private fun createNotification(
        message: String,
        notificationType: NotificationType
    ): NotificationDomain = NotificationDomain(0, message, OffsetDateTime.now(), notificationType, isRead = false)
}
