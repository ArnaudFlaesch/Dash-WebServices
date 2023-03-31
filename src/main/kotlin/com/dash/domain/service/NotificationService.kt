package com.dash.domain.service

import com.dash.domain.model.notification.NotificationDomain
import com.dash.domain.model.notification.NotificationType
import com.dash.infra.adapter.NotificationAdapter
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NotificationService(
    private val notificationAdapter: NotificationAdapter
) {

    fun notifyLogin(username: String) {
        val notification = createNotification("L'utilisateur $username s'est connecté.")
        notificationAdapter.saveNotification(notification)
    }

    private fun createNotification(message: String): NotificationDomain =
        NotificationDomain(0, message, LocalDateTime.now(), NotificationType.ALERT, isRead = false)
}
