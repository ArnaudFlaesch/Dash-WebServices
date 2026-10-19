package com.common.domain.event

import com.dash.domain.service.NotificationService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class DashEventListener(
    private val notificationService: NotificationService
) {
    @EventListener
    fun onDashEvent(event: DashEvent) {
        notificationService.saveNotification(event.messageContent, event.notificationType)
    }
}
