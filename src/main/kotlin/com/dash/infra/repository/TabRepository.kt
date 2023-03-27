package com.dash.infra.repository

import com.dash.infra.entity.TabEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TabRepository : JpaRepository<TabEntity, Int> {

    fun findByUserIdOrderByTabOrderAsc(userId: Int): List<TabEntity>

    @Query("SELECT COUNT(*) FROM TabEntity WHERE user.id = :userId")
    fun getNumberOfTabs(): Int
}
