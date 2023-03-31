package com.common.domain.event

import com.common.domain.model.EventTypeEnum
import com.dash.domain.service.NotificationService
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component


@Component
class EventListener(private val notificationService: NotificationService) : ApplicationListener<DashEvent> {

    override fun onApplicationEvent(event: DashEvent) {
        when (event.eventType) {
            EventTypeEnum.USER_LOGGED_IN -> notificationService.notifyLogin(event.messageContent)
        }
    }
}