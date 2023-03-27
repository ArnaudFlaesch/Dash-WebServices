package com.dash.domain.service

import com.common.domain.service.UserService
import com.dash.domain.model.TabDomain
import com.dash.infra.adapter.TabPersistenceAdapter
import org.springframework.stereotype.Service

@Service
class TabService(
    private val tabPersistenceAdapter: TabPersistenceAdapter,
    private val userService: UserService
) {

    fun getUserTabs(): List<TabDomain> {
        val userId = userService.getCurrentAuthenticatedUser().id
        return tabPersistenceAdapter.getUserTabs(userId)
    }

    fun addTab(tabLabel: String): TabDomain {
        val currentAuthenticatedUserId = userService.getCurrentAuthenticatedUser().id
        return tabPersistenceAdapter.addTab(tabLabel, currentAuthenticatedUserId)
    }

    fun saveTabs(tabList: List<TabDomain>): List<TabDomain> =
        tabPersistenceAdapter.saveTabs(tabList)

    fun importTab(tabLabel: String, tabOrder: Int): TabDomain {
        val user = userService.getCurrentAuthenticatedUser()
        val tabToInsert = TabDomain(0, tabLabel, tabOrder, userId = user.id)
        return tabPersistenceAdapter.importTab(tabToInsert)
    }

    fun updateTab(tabId: Int, label: String, tabOrder: Int): TabDomain =
        tabPersistenceAdapter.updateTab(tabId, label, tabOrder)

    fun deleteTab(id: Int) = tabPersistenceAdapter.deleteTab(id)
}
