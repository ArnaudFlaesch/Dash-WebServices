package com.dash.dashboard.adapter.outbound.persistence.notification

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
    var id: Int,
    var message: String,
    @Column(name = "notification_date") var notificationDate: OffsetDateTime,
    @Enumerated(EnumType.STRING)
    @Column(name = "notificationType")
    var notificationType: NotificationType,
    @Column(name = "is_read")
    var isRead: Boolean
)
