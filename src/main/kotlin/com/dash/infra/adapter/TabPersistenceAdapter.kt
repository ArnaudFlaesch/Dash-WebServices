package com.dash.infra.adapter

import com.common.infra.repository.UserRepository
import com.dash.domain.model.TabDomain
import com.dash.infra.entity.TabEntity
import com.dash.infra.repository.TabRepository
import com.dash.infra.repository.WidgetRepository
import org.springframework.stereotype.Component

@Component
class TabPersistenceAdapter(
    private val userRepository: UserRepository,
    private val tabRepository: TabRepository,
    private val widgetRepository: WidgetRepository
) {
    fun getUserTabs(userId: Int): List<TabDomain> =
        tabRepository
            .findByUserIdOrderByTabOrderAsc(userId)
            .map(TabEntity::toDomain)

    fun addTab(
        tabLabel: String,
        userId: Int
    ): TabDomain =
        TabEntity(
            id = 0,
            label = tabLabel,
            tabOrder = tabRepository.getNumberOfTabs(userId) + 1,
            user = userRepository.getReferenceById(userId)
        ).let(tabRepository::save)
            .let(TabEntity::toDomain)

    fun saveTabs(tabList: List<TabDomain>): List<TabDomain> =
        tabList
            .map(this::mapTabDomainToTabEntity)
            .map(tabRepository::save)
            .map(TabEntity::toDomain)

    fun importTab(newTab: TabDomain): TabDomain =
        TabEntity(
            id = 0,
            label = newTab.label,
            tabOrder = newTab.tabOrder,
            user = userRepository.getReferenceById(newTab.userId)
        ).let(tabRepository::save)
            .let(TabEntity::toDomain)

    fun updateTab(
        tabId: Int,
        label: String,
        tabOrder: Int
    ): TabDomain =
        tabRepository
            .getReferenceById(tabId)
            .copy(label = label, tabOrder = tabOrder)
            .let(tabRepository::save)
            .let(TabEntity::toDomain)

    fun deleteTab(tabId: Int) =
        widgetRepository
            .deleteWidgetsByTabId(tabId)
            .let { _ -> tabRepository.getReferenceById(tabId) }
            .let(tabRepository::delete)

    private fun mapTabDomainToTabEntity(tabDomain: TabDomain): TabEntity =
        TabEntity(
            id = tabDomain.id,
            label = tabDomain.label,
            tabOrder = tabDomain.tabOrder,
            user = userRepository.getReferenceById(tabDomain.userId)
        )
}
