package com.dash.dashboard.domain.model.notification

import com.dash.shared.domain.model.NotificationType
import java.time.OffsetDateTime

data class Notification(
    val id: Int,
    val message: String,
    val notificationDate: OffsetDateTime,
    val notificationType: NotificationType,
    val isRead: Boolean
) {
    fun markAsRead(): Notification = copy(isRead = true)

    companion object {
        /** An unread notification, not saved yet, about an action of the user named [username]. */
        fun newUserAction(
            username: String,
            message: String,
            notificationType: NotificationType,
            date: OffsetDateTime
        ): Notification =
            Notification(
                id = 0,
                message = "$username : $message",
                notificationDate = date,
                notificationType = notificationType,
                isRead = false
            )
    }
}
