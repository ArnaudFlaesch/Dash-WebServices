package com.dash.dashboard.application.service

import com.dash.dashboard.application.port.inbound.NotificationUseCase
import com.dash.dashboard.application.port.outbound.NotificationPersistencePort
import com.dash.dashboard.application.security.DashboardSecurityConditions
import com.dash.dashboard.domain.model.notification.Notification
import com.dash.shared.application.port.outbound.CurrentUserPort
import com.dash.shared.domain.model.NotificationType
import com.dash.shared.domain.model.Page
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.time.OffsetDateTime

@Service
class NotificationService(
    private val currentUserPort: CurrentUserPort,
    private val notificationPersistencePort: NotificationPersistencePort,
    private val clock: Clock
) : NotificationUseCase {
    @PreAuthorize(DashboardSecurityConditions.IS_USER_ADMIN)
    override fun getNotifications(
        pageNumber: Int,
        pageSize: Int
    ): Page<Notification> = notificationPersistencePort.getNotifications(pageNumber, pageSize)

    override fun saveNotification(
        message: String,
        notificationType: NotificationType
    ): Notification =
        Notification
            .newUserAction(currentUserPort.currentUsername(), message, notificationType, OffsetDateTime.now(clock))
            .let(notificationPersistencePort::saveNotification)

    @Transactional
    @PreAuthorize(DashboardSecurityConditions.IS_USER_ADMIN)
    override fun markNotificationsAsRead(notificationIds: List<Int>): List<Notification> =
        notificationPersistencePort
            .getNotificationsByIds(notificationIds)
            .map(Notification::markAsRead)
            .let(notificationPersistencePort::saveNotifications)
}
