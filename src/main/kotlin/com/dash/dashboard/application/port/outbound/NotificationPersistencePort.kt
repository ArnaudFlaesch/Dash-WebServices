package com.dash.dashboard.application.port.outbound

import com.dash.dashboard.domain.model.notification.Notification
import com.dash.shared.domain.model.Page

interface NotificationPersistencePort {
    /** Returns a page of the notifications, the most recent first. */
    fun getNotifications(
        pageNumber: Int,
        pageSize: Int
    ): Page<Notification>

    /** Returns the notifications with these ids, ignoring the ids that don't exist. */
    fun getNotificationsByIds(notificationIds: List<Int>): List<Notification>

    fun saveNotification(notification: Notification): Notification

    fun saveNotifications(notifications: List<Notification>): List<Notification>
}
