package com.dash.domain.service

import com.common.domain.service.UserService
import com.dash.domain.mapping.TabMapper
import com.dash.domain.model.TabDomain
import com.dash.infra.entity.RoleEntity
import com.dash.infra.entity.TabEntity
import com.dash.infra.entity.UserEntity
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

    fun getTabById(tabId: Int): TabEntity = tabRepository.getReferenceById(tabId)

    fun addTab(tabLabel: String): TabDomain {
        val currentAuthenticatedUser = userService.getCurrentAuthenticatedUser()
        val userEntity = UserEntity(
            id = currentAuthenticatedUser.id,
            email = currentAuthenticatedUser.email,
            username = currentAuthenticatedUser.username,
            password = "",
            role = RoleEntity(0, "")
        )
        val tabToInsert = TabEntity(id = 0, label = tabLabel, tabOrder = tabRepository.getNumberOfTabs() + 1, user = userEntity)
        return tabMapper.mapTabEntityToTabDomain(tabRepository.save(tabToInsert))
    }

    fun saveTabs(tabList: List<TabDomain>): List<TabDomain> =
        tabList
            .map(tabMapper::mapTabDomainToTabEntity)
            .map(tabRepository::save)
            .map(tabMapper::mapTabEntityToTabDomain)

    fun importTab(newTab: TabDomain): TabDomain {
        val newTab = TabEntity(id = 0, label = newTab.label, tabOrder = newTab.tabOrder, user = userService.getUserById(newTab.userId))
        return tabMapper.mapTabEntityToTabDomain(tabRepository.save(newTab))
    }

    fun updateTab(tabId: Int, label: String, tabOrder: Int): TabDomain {
        val oldTabToUpdate = tabRepository.getReferenceById(tabId)
        val updatedTab = oldTabToUpdate.copy(label = label, tabOrder = tabOrder)
        return tabMapper.mapTabEntityToTabDomain(tabRepository.save(updatedTab))
    }

    fun deleteTab(id: Int) {
        widgetRepository.deleteWidgetsByTabId(id)
        val tab = tabRepository.getReferenceById(id)
        return tabRepository.delete(tab)
    }
}
