package com.dash.domain.mapping

import com.common.domain.service.UserService
import com.dash.domain.model.TabDomain
import com.dash.infra.entity.TabEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TabMapper {

    @Autowired
    private lateinit var userService: UserService

    fun mapTabDomainToTabEntity(tabDomain: TabDomain): TabEntity = TabEntity(
        id = tabDomain.id,
        label = tabDomain.label,
        tabOrder = tabDomain.tabOrder,
        user = userService.getUserById(tabDomain.userId)
    )

    fun mapTabEntityToTabDomain(tabEntity: TabEntity): TabDomain = TabDomain(tabEntity.id, tabEntity.label, tabEntity.tabOrder, tabEntity.user.id)
}
