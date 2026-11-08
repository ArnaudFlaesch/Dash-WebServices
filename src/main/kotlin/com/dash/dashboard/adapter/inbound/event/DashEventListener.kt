package com.dash.dashboard.adapter.inbound.event

import com.dash.dashboard.application.port.inbound.NotificationUseCase
import com.dash.shared.domain.event.DashEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

/** Records the events published by every bounded context as notifications. */
@Component
class DashEventListener(
    private val notificationUseCase: NotificationUseCase
) {
    @EventListener
    fun onDashEvent(event: DashEvent) {
        notificationUseCase.saveNotification(event.messageContent, event.notificationType)
    }
}
