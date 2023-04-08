package com.dash.infra.repository

import com.common.utils.AbstractIT
import com.dash.domain.model.notification.NotificationType
import com.dash.infra.entity.NotificationEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable
import java.time.OffsetDateTime

@SpringBootTest
class NotificationRepositoryTests : AbstractIT() {

    @Autowired
    private lateinit var notificationRepository: NotificationRepository

    @After
    fun tearDown() {
        notificationRepository.deleteAll()
    }

    @Test
    fun testCreateNotifications() {
        val notifications = listOf(
            NotificationEntity(0, "Test notif", OffsetDateTime.now(), NotificationType.WARN.name, false),
            NotificationEntity(0, "Test notif 2", OffsetDateTime.now(), NotificationType.INFO.name, true)
        )
        notificationRepository.saveAll(notifications)

        val listNotifications = notificationRepository.findAllByOrderByNotificationDateDesc(Pageable.ofSize(10))

        assertThat(listNotifications.content).hasSize(2)
        assertNotNull(listNotifications.content[0].id)
        assertEquals("Test notif", listNotifications.content[0].message)
        assertEquals(NotificationType.WARN.name, listNotifications.content[0].notificationType)
        assertEquals(false, listNotifications.content[0].isRead)
    }
}
