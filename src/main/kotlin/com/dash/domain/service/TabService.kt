package com.dash.domain.service

import com.common.domain.service.UserService
import com.dash.domain.model.TabDomain
import com.dash.infra.entity.RoleEntity
import com.dash.infra.entity.TabEntity
import com.dash.infra.entity.UserEntity
import com.dash.infra.repository.TabRepository
import com.dash.infra.repository.WidgetRepository
import org.springframework.stereotype.Service

@Service
class TabService(
    private val tabRepository: TabRepository,
    private val widgetRepository: WidgetRepository,
    private val userService: UserService
) {

    fun getTabs(): List<TabDomain> {
        val userId = userService.getCurrentAuthenticatedUser().id
        return tabRepository.findByUserIdOrderByTabOrderAsc(userId).map(TabEntity::toDomain)
    }

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
        return tabRepository.save(tabToInsert).toDomain()
    }

    fun saveTabs(tabList: List<TabDomain>): List<TabDomain> =
        tabList
            .map(this::mapTabDomainToTabEntity)
            .map(tabRepository::save)
            .map(TabEntity::toDomain)

    fun importTab(newTab: TabDomain): TabDomain {
        val tabToInsert = TabEntity(id = 0, label = newTab.label, tabOrder = newTab.tabOrder, user = userService.getUserById(newTab.userId))
        return tabRepository.save(tabToInsert).toDomain()
    }

    fun updateTab(tabId: Int, label: String, tabOrder: Int): TabDomain {
        val oldTabToUpdate = tabRepository.getReferenceById(tabId)
        val updatedTab = oldTabToUpdate.copy(label = label, tabOrder = tabOrder)
        return tabRepository.save(updatedTab).toDomain()
    }

    fun deleteTab(id: Int) {
        widgetRepository.deleteWidgetsByTabId(id)
        val tab = tabRepository.getReferenceById(id)
        return tabRepository.delete(tab)
    }

    private fun mapTabDomainToTabEntity(tabDomain: TabDomain): TabEntity = TabEntity(
        id = tabDomain.id,
        label = tabDomain.label,
        tabOrder = tabDomain.tabOrder,
        user = userService.getUserById(tabDomain.userId)
    )
}
