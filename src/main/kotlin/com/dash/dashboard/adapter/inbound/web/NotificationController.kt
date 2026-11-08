package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.notification.MarkNotificationsAsReadRequest
import com.dash.dashboard.application.port.inbound.NotificationUseCase
import com.dash.dashboard.domain.model.notification.Notification
import com.dash.shared.domain.model.Page
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notifications")
class NotificationController(
    private val notificationUseCase: NotificationUseCase
) {
    @GetMapping
    fun getNotifications(
        @RequestParam(defaultValue = "0") pageNumber: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): Page<Notification> = notificationUseCase.getNotifications(pageNumber, pageSize)

    @PutMapping("/read")
    fun markNotificationAsRead(
        @RequestBody markNotificationsAsReadRequest: MarkNotificationsAsReadRequest
    ): List<Notification> = notificationUseCase.markNotificationsAsRead(markNotificationsAsReadRequest.notificationIds)
}
