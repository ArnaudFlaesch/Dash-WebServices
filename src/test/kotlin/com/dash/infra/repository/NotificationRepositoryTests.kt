package com.dash.infra.repository

import com.common.utils.AbstractIT
import com.dash.domain.model.notification.NotificationType
import com.dash.infra.entity.NotificationEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable
import java.time.OffsetDateTime

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NotificationRepositoryTests : AbstractIT() {
    @Autowired
    private lateinit var notificationRepository: NotificationRepository

    @BeforeAll
    @AfterAll
    fun tearDown() {
        notificationRepository.deleteAll()
    }

    @Test
    fun testCreateNotifications() {
        val notifications =
            listOf(
                NotificationEntity(0, "Test notif", OffsetDateTime.now(), NotificationType.WARN.name, false),
                NotificationEntity(0, "Test notif 2", OffsetDateTime.now(), NotificationType.INFO.name, true)
            )
        notificationRepository.saveAll(notifications)

        val listNotifications = notificationRepository.findAllByOrderByNotificationDateDesc(Pageable.ofSize(10))

        assertThat(listNotifications.content).hasSize(2)
        assertNotNull(listNotifications.content[0].id)
        assertThat(listNotifications.content.map(NotificationEntity::message)).containsExactlyInAnyOrder("Test notif", "Test notif 2")
        assertThat(listNotifications.content.map(NotificationEntity::notificationType)).containsExactlyInAnyOrder(
            NotificationType.WARN.name,
            NotificationType.INFO.name
        )
        assertThat(listNotifications.content.map(NotificationEntity::isRead)).containsExactlyInAnyOrder(false, true)
    }
}
