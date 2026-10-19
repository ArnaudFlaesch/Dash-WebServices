package com.dash.infra.repository

import com.dash.infra.entity.NotificationEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository : JpaRepository<NotificationEntity, Int> {
    fun findAllByOrderByNotificationDateDesc(pageRequest: Pageable): Page<NotificationEntity>
}
