package com.dash.dashboard.application.port.inbound

import com.dash.dashboard.domain.model.notification.Notification
import com.dash.shared.domain.model.NotificationType
import com.dash.shared.domain.model.Page

interface NotificationUseCase {
    fun getNotifications(
        pageNumber: Int,
        pageSize: Int
    ): Page<Notification>

    /** Records a notification about an action of the authenticated user. */
    fun saveNotification(
        message: String,
        notificationType: NotificationType
    ): Notification

    fun markNotificationsAsRead(notificationIds: List<Int>): List<Notification>
}
