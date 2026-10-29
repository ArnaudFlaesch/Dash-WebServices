package com.dash.infra.adapter

import com.common.infra.repository.UserRepository
import com.dash.domain.model.TabDomain
import com.dash.infra.entity.TabEntity
import com.dash.infra.repository.TabRepository
import com.dash.infra.repository.WidgetRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

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
    ): TabDomain = saveTab(TabDomain(0, tabLabel, tabRepository.getNumberOfTabs(userId) + 1, userId))

    fun saveTabs(tabList: List<TabDomain>): List<TabDomain> = tabList.map(::saveTab)

    fun importTab(newTab: TabDomain): TabDomain = saveTab(newTab.copy(id = 0))

    @Transactional
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

    @Transactional
    fun deleteTab(tabId: Int) {
        widgetRepository.deleteWidgetsByTabId(tabId)
        tabRepository.deleteById(tabId)
    }

    private fun saveTab(tab: TabDomain): TabDomain =
        TabEntity(
            id = tab.id,
            label = tab.label,
            tabOrder = tab.tabOrder,
            user = userRepository.getReferenceById(tab.userId)
        ).let(tabRepository::save)
            .let(TabEntity::toDomain)
}
