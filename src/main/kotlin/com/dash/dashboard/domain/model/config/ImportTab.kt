package com.dash.dashboard.domain.model.config

import com.dash.dashboard.domain.model.tab.Tab

data class ImportTab(
    val id: Int,
    val label: String,
    val tabOrder: Int
) {
    /** The tab to import for the user, at its exported position. */
    fun toNewTab(userId: Int): Tab = Tab(id = 0, label = label, tabOrder = tabOrder, userId = userId)
}
