package com.dash.infra.adapter

import com.common.infra.repository.UserRepository
import com.dash.domain.model.TabDomain
import com.dash.infra.entity.TabEntity
import com.dash.infra.entity.WidgetEntity
import com.dash.infra.repository.TabRepository
import com.dash.infra.repository.WidgetRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TabPersistenceAdapter(
    private val userRepository: UserRepository,
    private val tabRepository: TabRepository,
    private val widgetRepository: WidgetRepository,
    private val widgetPersistenceAdapter: WidgetPersistenceAdapter
) {
    fun getUserTabs(userId: Int): List<TabDomain> =
        tabRepository
            .findByUserIdOrderByTabOrderAsc(userId)
            .map(TabEntity::toDomain)

    @Transactional
    fun addTab(
        tabLabel: String,
        userId: Int
    ): TabDomain = createTab(tabLabel, tabRepository.getNumberOfTabs(userId) + 1, userId)

    fun importTab(
        tabLabel: String,
        tabOrder: Int,
        userId: Int
    ): TabDomain = createTab(tabLabel, tabOrder, userId)

    /** Updates the label and order of existing tabs. The tabs' owner is never changed. */
    @Transactional
    fun updateTabs(tabList: List<TabDomain>): List<TabDomain> =
        tabList.map { tab -> updateTab(tab.id, tab.label, tab.tabOrder) }

    @Transactional
    fun updateTab(
        tabId: Int,
        label: String,
        tabOrder: Int
    ): TabDomain =
        tabRepository
            .getReferenceById(tabId)
            .apply {
                this.label = label
                this.tabOrder = tabOrder
            }.toDomain()

    @Transactional
    fun deleteTab(tabId: Int) {
        val widgetIds = widgetRepository.findByTabIdOrderByWidgetOrderAsc(tabId).map(WidgetEntity::id)
        widgetPersistenceAdapter.deleteWidgetsData(widgetIds)
        widgetRepository.deleteWidgetsByTabId(tabId)
        tabRepository.deleteById(tabId)
    }

    private fun createTab(
        label: String,
        tabOrder: Int,
        userId: Int
    ): TabDomain =
        TabEntity(
            id = 0,
            label = label,
            tabOrder = tabOrder,
            user = userRepository.getReferenceById(userId)
        ).let(tabRepository::save)
            .let(TabEntity::toDomain)
}
