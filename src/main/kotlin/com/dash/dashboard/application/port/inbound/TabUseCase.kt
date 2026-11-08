package com.dash.dashboard.application.port.inbound

import com.dash.dashboard.domain.model.tab.Tab

interface TabUseCase {
    fun getUserTabs(): List<Tab>

    fun addTab(tabLabel: String): Tab

    fun saveTabs(tabList: List<Tab>): List<Tab>

    fun updateTab(
        tabId: Int,
        label: String,
        tabOrder: Int
    ): Tab

    /** Deletes the tab and its widgets. */
    fun deleteTab(tabId: Int)
}
