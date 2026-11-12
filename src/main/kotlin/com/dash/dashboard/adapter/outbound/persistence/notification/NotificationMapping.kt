package com.dash.dashboard.adapter.outbound.persistence.notification

import com.dash.dashboard.domain.model.notification.Notification

fun NotificationEntity.toDomain(): Notification =
    Notification(
        id = id,
        message = message,
        notificationDate = notificationDate,
        notificationType = notificationType,
        isRead = isRead
    )

fun Notification.toEntity(): NotificationEntity =
    NotificationEntity(
        id = id,
        message = message,
        notificationDate = notificationDate,
        notificationType = notificationType,
        isRead = isRead
    )
