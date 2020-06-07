package com.dash.repository

import com.dash.entity.Widget
import org.springframework.data.jpa.repository.JpaRepository

interface WidgetRepository : JpaRepository<Widget, Long> {

}