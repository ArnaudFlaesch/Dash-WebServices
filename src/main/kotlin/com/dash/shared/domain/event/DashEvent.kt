package com.dash.shared.domain.event

import com.dash.shared.domain.model.NotificationType

data class DashEvent(
    val messageContent: String,
    val notificationType: NotificationType
)
