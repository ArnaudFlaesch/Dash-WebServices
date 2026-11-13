package com.dash.dashboard.application.port.outbound

import com.dash.dashboard.domain.model.tab.Tab

interface TabPersistencePort {
    fun getUserTabs(userId: Int): List<Tab>

    fun getNumberOfTabs(userId: Int): Int

    /** Saves a new tab, returning it with its id. */
    fun createTab(tab: Tab): Tab

    /** Updates the label and order of existing tabs. The tabs' owner is never changed. */
    fun updateTabs(tabList: List<Tab>): List<Tab>

    fun updateTab(
        tabId: Int,
        label: String,
        tabOrder: Int
    ): Tab

    /** Deletes the tab, which must have no widget left. */
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
