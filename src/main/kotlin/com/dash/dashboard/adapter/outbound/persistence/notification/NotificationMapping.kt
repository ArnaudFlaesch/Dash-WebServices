package com.dash.dashboard.adapter.outbound.persistence.notification

import com.dash.dashboard.domain.model.notification.Notification
import com.dash.shared.domain.model.NotificationType
import jakarta.persistence.*
import java.time.OffsetDateTime

fun NotificationEntity.toDomain(): Notification =
    Notification(
        id = id,
        message = message,
        notificationDate = notificationDate,
        notificationType = notificationType,
        isRead = isRead
    )
