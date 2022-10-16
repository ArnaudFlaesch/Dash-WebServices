package com.dash.infra.repository

import com.dash.infra.entity.Tab
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TabRepository : JpaRepository<Tab, Int> {

    fun findByUserIdOrderByTabOrderAsc(userId: Int): List<Tab>

    @Query("SELECT COUNT(*) FROM Tab")
    fun getNumberOfTabs(): Int
}
