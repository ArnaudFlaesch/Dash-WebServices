package com.dash.infra.entity

import com.dash.domain.model.notification.NotificationDomain
import com.dash.domain.model.notification.NotificationType
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "notification")
data class NotificationEntity(
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

    @Column(name = "notificationType") val notificationType: String,

    @Column(name = "is_read")
    val isRead: Boolean
) {
    fun toDomain(): NotificationDomain = NotificationDomain(
        id = id,
        message = message,
        notificationDate = notificationDate,
        notificationType = NotificationType.valueOf(notificationType),
        isRead = isRead
    )
}
