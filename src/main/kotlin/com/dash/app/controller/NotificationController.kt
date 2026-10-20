package com.dash.app.controller

import com.dash.app.controller.requests.notifications.MarkNotificationsAsReadPayload
import com.dash.app.controller.response.Page
import com.dash.app.controller.response.toPageResponse
import com.dash.domain.model.notification.NotificationDomain
import com.dash.domain.service.NotificationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notifications")
class NotificationController(
    private val notificationService: NotificationService
) {
    @GetMapping("/")
    fun getNotifications(
        @RequestParam(defaultValue = "0") pageNumber: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): Page<NotificationDomain> = notificationService.getNotifications(pageNumber, pageSize).toPageResponse()

    @PutMapping("/markNotificationAsRead")
    fun markNotificationAsRead(
        @RequestBody markNotificationsAsReadPayload: MarkNotificationsAsReadPayload
    ): List<NotificationDomain> =
        notificationService.markNotificationsAsRead(markNotificationsAsReadPayload.notificationIds)
}
