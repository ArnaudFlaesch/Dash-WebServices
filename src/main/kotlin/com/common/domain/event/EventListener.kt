package com.common.domain.event

import com.dash.domain.service.NotificationService
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class EventListener(
    private val notificationService: NotificationService
) : ApplicationListener<DashEvent> {
    override fun onApplicationEvent(event: DashEvent) {
        notificationService.saveNotification(event.messageContent, event.notificationType)
    }
}
