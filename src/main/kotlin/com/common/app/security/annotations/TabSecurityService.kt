package com.common.app.security.annotations

import com.common.domain.service.UserService
import com.dash.domain.model.TabDomain
import com.dash.infra.adapter.TabPersistenceAdapter
import org.springframework.stereotype.Component

@Component
class TabSecurityService(
    val userService: UserService,
    val tabPersistenceAdapter: TabPersistenceAdapter
) {
    fun doesTabBelongToUser(tabId: Int): Boolean =
        userService
            .getCurrentAuthenticatedUserId()
            .let(tabPersistenceAdapter::getUserTabs)
            .map(TabDomain::id)
            .contains(tabId)
}
