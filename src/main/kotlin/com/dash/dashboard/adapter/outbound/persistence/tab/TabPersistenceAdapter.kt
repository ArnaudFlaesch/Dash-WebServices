package com.dash.dashboard.adapter.outbound.persistence.tab

import com.dash.dashboard.application.port.outbound.TabPersistencePort
import com.dash.dashboard.domain.model.tab.Tab
import com.dash.shared.adapter.outbound.persistence.user.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TabPersistenceAdapter(
    private val userRepository: UserRepository,
    private val tabRepository: TabRepository
) : TabPersistencePort {
    override fun getUserTabs(userId: Int): List<Tab> =
        tabRepository
            .findByUserIdOrderByTabOrderAsc(userId)
            .map(TabEntity::toDomain)

    override fun getNumberOfTabs(userId: Int): Int = tabRepository.getNumberOfTabs(userId)

    override fun createTab(tab: Tab): Tab =
        TabEntity(
            id = 0,
            label = tab.label,
            tabOrder = tab.tabOrder,
            user = userRepository.getReferenceById(tab.userId)
        ).let(tabRepository::save)
            .let(TabEntity::toDomain)

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

    override fun deleteTab(tabId: Int) {
        tabRepository.deleteById(tabId)
    }

    override fun isTabOwnedBy(
        tabId: Int,
        userId: Int
    ): Boolean = tabRepository.existsByIdAndUserId(tabId, userId)

    override fun findTabIdsOwnedBy(
        tabIds: Collection<Int>,
        userId: Int
    ): Collection<Int> = tabRepository.findIdsByIdInAndUserId(tabIds, userId)
}
