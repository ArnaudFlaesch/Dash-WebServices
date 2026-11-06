package com.dash.domain.service

import com.common.app.security.SecurityConditions
import com.common.domain.service.UserService
import com.dash.domain.model.TabDomain
import com.dash.infra.adapter.TabPersistenceAdapter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class TabService(
    private val tabPersistenceAdapter: TabPersistenceAdapter,
    private val userService: UserService
) {
    fun getUserTabs(): List<TabDomain> = tabPersistenceAdapter.getUserTabs(userService.getCurrentAuthenticatedUserId())

    fun addTab(tabLabel: String): TabDomain =
        tabPersistenceAdapter.addTab(tabLabel, userService.getCurrentAuthenticatedUserId())

    @PreAuthorize(SecurityConditions.DO_TABS_BELONG_TO_AUTHENTICATED_USER)
    fun saveTabs(tabList: List<TabDomain>): List<TabDomain> = tabPersistenceAdapter.updateTabs(tabList)

    fun importTab(
        tabLabel: String,
        tabOrder: Int
    ): TabDomain = tabPersistenceAdapter.importTab(tabLabel, tabOrder, userService.getCurrentAuthenticatedUserId())

    @PreAuthorize(SecurityConditions.DOES_TAB_BELONG_TO_AUTHENTICATED_USER)
    fun updateTab(
        tabId: Int,
        label: String,
        tabOrder: Int
    ): TabDomain = tabPersistenceAdapter.updateTab(tabId, label, tabOrder)

    @PreAuthorize(
        "${SecurityConditions.DOES_TAB_BELONG_TO_AUTHENTICATED_USER} and ${SecurityConditions.IS_USER_ADMIN}"
    )
    fun deleteTab(tabId: Int) = tabPersistenceAdapter.deleteTab(tabId)
}
