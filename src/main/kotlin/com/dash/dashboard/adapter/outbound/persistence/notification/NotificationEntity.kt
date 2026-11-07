package com.dash.dashboard.adapter.outbound.persistence.notification

import com.dash.dashboard.domain.model.notification.Notification
import com.dash.shared.domain.model.NotificationType
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "notification")
class NotificationEntity(
    @Id
    @SequenceGenerator(
        name = "notification-seq-gen",
        sequenceName = "notification_id_seq",
        initialValue = 1,
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,
    val message: String,
    @Column(name = "notification_date") val notificationDate: OffsetDateTime,
    @Enumerated(EnumType.STRING)
    @Column(name = "notificationType")
    val notificationType: NotificationType,
    @Column(name = "is_read")
    var isRead: Boolean
) {
    fun toDomain(): Notification =
        Notification(
            id = id,
            message = message,
            notificationDate = notificationDate,
            notificationType = notificationType,
            isRead = isRead
        )
}
