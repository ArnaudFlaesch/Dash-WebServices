package com.dash.app.controller

import com.dash.app.controller.requests.notifications.MarkNotificationsAsReadPayload
import com.dash.app.controller.response.Page
import com.dash.domain.model.notification.NotificationDomain
import com.dash.domain.service.NotificationService
import com.dash.utils.PageMapper
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = ["*"])
class NotificationController(
    private val notificationService: NotificationService
) {
    @GetMapping("/")
    fun getNotifications(
        @RequestParam(value = "pageNumber", defaultValue = "0") pageNumber: Int,
        @RequestParam(value = "pageSize", defaultValue = "10") pageSize: Int
    ): Page<NotificationDomain> = PageMapper.mapPageToPageResponse(notificationService.getNotifications(pageNumber, pageSize))

    @PutMapping("/markNotificationAsRead")
    fun markNotificationAsRead(
        @RequestBody markNotificationsAsReadPayload: MarkNotificationsAsReadPayload
    ): List<NotificationDomain> = notificationService.markNotificationsAsRead(markNotificationsAsReadPayload.notificationIds)
}
