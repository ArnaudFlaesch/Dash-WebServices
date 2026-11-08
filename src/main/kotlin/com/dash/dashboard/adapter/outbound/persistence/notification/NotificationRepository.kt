package com.dash.dashboard.adapter.outbound.persistence.notification

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : JpaRepository<NotificationEntity, Int> {
    fun findAllByOrderByNotificationDateDesc(pageRequest: Pageable): Page<NotificationEntity>
}
