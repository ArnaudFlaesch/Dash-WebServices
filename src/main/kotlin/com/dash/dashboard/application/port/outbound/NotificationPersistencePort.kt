package com.dash.dashboard.application.port.outbound

import com.dash.dashboard.domain.model.notification.Notification
import com.dash.shared.domain.model.Page

interface NotificationPersistencePort {
    /** Returns a page of the notifications, the most recent first. */
    fun getNotifications(
        pageNumber: Int,
        pageSize: Int
    ): Page<Notification>

    fun saveNotification(notification: Notification): Notification

    fun markNotificationsAsRead(notificationIds: List<Int>): List<Notification>
}
