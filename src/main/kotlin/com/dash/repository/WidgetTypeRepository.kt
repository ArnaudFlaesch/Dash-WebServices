package com.dash.repository

import com.dash.entity.WidgetType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WidgetTypeRepository : JpaRepository<WidgetType, Int>
