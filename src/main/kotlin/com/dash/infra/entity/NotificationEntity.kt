package com.dash.infra.entity

import com.dash.domain.model.notification.NotificationDomain
import com.dash.domain.model.notification.NotificationType
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
    fun toDomain(): NotificationDomain =
        NotificationDomain(
            id = id,
            message = message,
            notificationDate = notificationDate,
            notificationType = notificationType,
            isRead = isRead
        )
}
