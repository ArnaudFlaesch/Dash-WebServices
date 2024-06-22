package com.common.domain.event

import com.dash.domain.model.notification.NotificationType
import org.springframework.context.ApplicationEvent

class DashEvent(
    source: Any,
    val messageContent: String,
    val notificationType: NotificationType
) : ApplicationEvent(source)
