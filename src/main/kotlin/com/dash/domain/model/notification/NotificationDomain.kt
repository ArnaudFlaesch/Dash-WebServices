package com.dash.domain.model.notification

import java.time.OffsetDateTime

data class NotificationDomain(
    val id: Int,
    val message: String,
    val notificationDate: OffsetDateTime,
    val notificationType: NotificationType,
    val isRead: Boolean
)
