package com.dash.dashboard.application.service

import com.dash.dashboard.application.port.inbound.TabUseCase
import com.dash.dashboard.application.port.outbound.TabPersistencePort
import com.dash.dashboard.application.port.outbound.WidgetPersistencePort
import com.dash.dashboard.application.security.DashboardSecurityConditions
import com.dash.dashboard.domain.model.tab.Tab
import com.dash.shared.application.port.outbound.CurrentUserPort
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TabService(
    private val tabPersistencePort: TabPersistencePort,
    private val widgetPersistencePort: WidgetPersistencePort,
    private val currentUserPort: CurrentUserPort
) : TabUseCase {
    @PostFilter(DashboardSecurityConditions.DOES_FILTERED_TAB_BELONG_TO_AUTHENTICATED_USER)
    override fun getUserTabs(): List<Tab> = tabPersistencePort.getUserTabs(currentUserPort.currentUserId())

    @Transactional
    override fun addTab(tabLabel: String): Tab =
        currentUserPort.currentUserId().let { userId ->
            Tab
                .newAfter(tabPersistencePort.getNumberOfTabs(userId), tabLabel, userId)
                .let(tabPersistencePort::createTab)
        }

    @PreAuthorize(DashboardSecurityConditions.DO_TABS_BELONG_TO_AUTHENTICATED_USER)
    override fun saveTabs(tabList: List<Tab>): List<Tab> = tabPersistencePort.updateTabs(tabList)

    @PreAuthorize(DashboardSecurityConditions.DOES_TAB_BELONG_TO_AUTHENTICATED_USER)
    override fun updateTab(
        tabId: Int,
        label: String,
        tabOrder: Int
    ): Tab = tabPersistencePort.updateTab(tabId, label, tabOrder)

    @Transactional
    @PreAuthorize(
        "${DashboardSecurityConditions.DOES_TAB_BELONG_TO_AUTHENTICATED_USER} and " +
            DashboardSecurityConditions.IS_USER_ADMIN
    )
    override fun deleteTab(tabId: Int) {
        widgetPersistencePort.deleteTabWidgets(tabId)
        tabPersistencePort.deleteTab(tabId)
    }
}
