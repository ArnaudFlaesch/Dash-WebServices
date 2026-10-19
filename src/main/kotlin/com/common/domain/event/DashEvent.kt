package com.common.domain.event

import com.dash.domain.model.notification.NotificationType

data class DashEvent(
    val messageContent: String,
    val notificationType: NotificationType
)
