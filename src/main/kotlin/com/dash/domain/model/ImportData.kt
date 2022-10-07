package com.dash.domain.model

import com.dash.infra.entity.Tab
import com.dash.infra.entity.Widget

data class ImportData(
    val widgets: List<Widget> = listOf(),

    val tabs: List<Tab> = listOf()
)
