package com.dash.domain.service

import com.common.domain.service.UserService
import com.dash.domain.mapping.TabMapper
import com.dash.domain.model.TabDomain
import com.dash.infra.entity.TabEntity
import com.dash.infra.repository.TabRepository
import com.dash.infra.repository.WidgetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TabService {

    @Autowired
    private lateinit var tabRepository: TabRepository

    @Autowired
    private lateinit var widgetRepository: WidgetRepository

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var tabMapper: TabMapper

    fun getTabs(): List<TabDomain> {
        val userId = userService.getCurrentAuthenticatedUserId()
        return tabRepository.findByUserIdOrderByTabOrderAsc(userId).map(tabMapper::mapTabEntityToTabDomain)
    }

    fun getTabById(tabId: Int) = tabRepository.getReferenceById(tabId)

    fun addTab(tabLabel: String): TabDomain {
        val currentAuthenticatedUser = userService.getCurrentAuthenticatedUser()
        val tabToInsert = TabEntity(id = 0, label = tabLabel, tabOrder = tabRepository.getNumberOfTabs() + 1, user = currentAuthenticatedUser)
        return tabMapper.mapTabEntityToTabDomain(tabRepository.save(tabToInsert))
    }

    fun saveTabs(tabList: List<TabDomain>): List<TabDomain> =
        tabList
            .map(tabMapper::mapTabDomainToTabEntity)
            .map(tabRepository::save)
            .map(tabMapper::mapTabEntityToTabDomain)

    fun saveTab(tabEntity: TabDomain): TabDomain = tabMapper.mapTabEntityToTabDomain(tabRepository.save(tabMapper.mapTabDomainToTabEntity(tabEntity)))

    fun deleteTab(id: Int) {
        widgetRepository.deleteWidgetsByTabId(id)
        val tab = tabRepository.getReferenceById(id)
        return tabRepository.delete(tab)
    }
}
