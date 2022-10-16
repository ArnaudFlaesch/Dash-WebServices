package com.dash.domain.service

import com.common.domain.service.UserService
import com.dash.infra.entity.Tab
import com.dash.infra.repository.TabRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TabService {

    @Autowired
    private lateinit var tabRepository: TabRepository

    @Autowired
    private lateinit var widgetService: WidgetService

    @Autowired
    private lateinit var userService: UserService

    fun getTabs(): List<Tab> {
        val userId = userService.getCurrentAuthenticatedUserId()
        return tabRepository.findByUserIdOrderByTabOrderAsc(userId)
    }

    fun addTab(tabLabel: String): Tab {
        val currentAuthenticatedUser = userService.getCurrentAuthenticatedUser()
        return tabRepository.save(Tab(id = 0, label = tabLabel, tabOrder = tabRepository.getNumberOfTabs() + 1, user = currentAuthenticatedUser))
    }

    fun saveTabs(tabs: List<Tab>): List<Tab> = tabs.map { tab -> tabRepository.save(tab) }

    fun updateTab(tab: Tab): Tab = tabRepository.save(tab)

    fun deleteTab(id: Int) {
        widgetService.deleteWidgetsByTabId(id)
        val tab = tabRepository.getReferenceById(id)
        return tabRepository.delete(tab)
    }
}
