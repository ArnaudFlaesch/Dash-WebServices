package com.dash.domain.model.notification

import java.time.LocalDateTime

data class NotificationDomain(
    val id: Int,
    val message: String,
    val notificationDate: LocalDateTime,
    val notificationType: NotificationType,
    val isRead: Boolean
)
