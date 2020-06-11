package com.dash.repository

import com.dash.entity.Tab
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TabRepository : JpaRepository<Tab, Int> {
    fun findByOrderByTabOrderAsc(): List<Tab>
}
