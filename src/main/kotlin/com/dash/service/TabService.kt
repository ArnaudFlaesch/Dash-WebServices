package com.dash.service

import com.dash.entity.Tab
import com.dash.repository.TabRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TabService {

    @Autowired
    private lateinit var tabRepository: TabRepository

    @Autowired
    private lateinit var widgetService: WidgetService

    fun getTabs(): List<Tab> =      tabRepository.findByOrderByTabOrderAsc()

    fun addTab(tabToAdd: Tab): Tab {
        tabToAdd.tabOrder = tabRepository.getNumberOfTabs() + 1
        return tabRepository.save(tabToAdd)
    }

    fun saveTabs(tabs: List<Tab>): List<Tab> = tabs.map { tab -> tabRepository.save(tab) }

    fun updateTab(tab: Tab): Tab = tabRepository.save(tab)

    fun deleteTab(id: Int) {
        widgetService.deleteWidgetsByTabId(id)
        val tab = tabRepository.getOne(id)
        return tabRepository.delete(tab)
    }
}
