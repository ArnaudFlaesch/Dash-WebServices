package com.dash.dashboard.adapter.outbound.persistence.tab

import com.dash.dashboard.adapter.outbound.persistence.widget.WidgetEntity
import com.dash.dashboard.adapter.outbound.persistence.widget.WidgetPersistenceAdapter
import com.dash.dashboard.adapter.outbound.persistence.widget.WidgetRepository
import com.dash.dashboard.application.port.outbound.TabPersistencePort
import com.dash.dashboard.domain.model.tab.Tab
import com.dash.shared.adapter.outbound.persistence.user.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TabPersistenceAdapter(
    private val userRepository: UserRepository,
    private val tabRepository: TabRepository,
    private val widgetRepository: WidgetRepository,
    private val widgetPersistenceAdapter: WidgetPersistenceAdapter
) : TabPersistencePort {
    override fun getUserTabs(userId: Int): List<Tab> =
        tabRepository
            .findByUserIdOrderByTabOrderAsc(userId)
            .map(TabEntity::toDomain)

    @Transactional
    override fun addTab(
        tabLabel: String,
        userId: Int
    ): Tab = createTab(tabLabel, tabRepository.getNumberOfTabs(userId) + 1, userId)

    override fun importTab(
        tabLabel: String,
        tabOrder: Int,
        userId: Int
    ): Tab = createTab(tabLabel, tabOrder, userId)

    @Transactional
    override fun updateTabs(tabList: List<Tab>): List<Tab> =
        tabList.map { tab -> updateTab(tab.id, tab.label, tab.tabOrder) }

    @Transactional
    override fun updateTab(
        tabId: Int,
        label: String,
        tabOrder: Int
    ): Tab =
        tabRepository
            .getReferenceById(tabId)
            .apply {
                this.label = label
                this.tabOrder = tabOrder
            }.toDomain()

    @Transactional
    override fun deleteTab(tabId: Int) {
        widgetRepository
            .findByTabIdOrderByWidgetOrderAsc(tabId)
            .map(WidgetEntity::id)
            .let(widgetPersistenceAdapter::deleteWidgetsData)
        widgetRepository.deleteWidgetsByTabId(tabId)
        tabRepository.deleteById(tabId)
    }

    private fun createTab(
        label: String,
        tabOrder: Int,
        userId: Int
    ): Tab =
        TabEntity(
            id = 0,
            label = label,
            tabOrder = tabOrder,
            user = userRepository.getReferenceById(userId)
        ).let(tabRepository::save)
            .let(TabEntity::toDomain)

    override fun isTabOwnedBy(
        tabId: Int,
        userId: Int
    ): Boolean = tabRepository.existsByIdAndUserId(tabId, userId)

    override fun findTabIdsOwnedBy(
        tabIds: Collection<Int>,
        userId: Int
    ): Collection<Int> = tabRepository.findIdsByIdInAndUserId(tabIds, userId)
}
