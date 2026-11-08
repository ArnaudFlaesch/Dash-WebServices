package com.dash.dashboard.domain.model.notification

import com.dash.shared.domain.model.NotificationType
import java.time.OffsetDateTime

data class Notification(
    val id: Int,
    val message: String,
    val notificationDate: OffsetDateTime,
    val notificationType: NotificationType,
    val isRead: Boolean
)
