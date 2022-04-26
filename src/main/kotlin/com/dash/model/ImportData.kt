package com.dash.model

import com.dash.entity.Tab
import com.dash.entity.Widget

data class ImportData(
    val widgets: List<Widget> = listOf(),

    val tabs: List<Tab> = listOf()
)
