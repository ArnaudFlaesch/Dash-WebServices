package com.dash.repository

import com.dash.entity.Tab
import org.springframework.data.jpa.repository.JpaRepository

interface TabRepository : JpaRepository<Tab, Long> {

}