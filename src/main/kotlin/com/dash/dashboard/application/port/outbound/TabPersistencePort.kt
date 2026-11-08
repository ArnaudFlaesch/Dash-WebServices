package com.dash.dashboard.application.port.outbound

import com.dash.dashboard.domain.model.tab.Tab

interface TabPersistencePort {
    fun getUserTabs(userId: Int): List<Tab>

    /** Adds a tab at the end of the user's tabs. */
    fun addTab(
        tabLabel: String,
        userId: Int
    ): Tab

    fun importTab(
        tabLabel: String,
        tabOrder: Int,
        userId: Int
    ): Tab

    /** Updates the label and order of existing tabs. The tabs' owner is never changed. */
    fun updateTabs(tabList: List<Tab>): List<Tab>

    fun updateTab(
        tabId: Int,
        label: String,
        tabOrder: Int
    ): Tab

    /** Deletes the tab, its widgets and their data. */
    fun deleteTab(tabId: Int)

    fun isTabOwnedBy(
        tabId: Int,
        userId: Int
    ): Boolean

    fun findTabIdsOwnedBy(
        tabIds: Collection<Int>,
        userId: Int
    ): Collection<Int>
}
