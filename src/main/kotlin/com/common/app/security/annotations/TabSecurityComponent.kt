package com.common.app.security.annotations

import com.common.domain.model.UserDomain
import org.springframework.stereotype.Component

@Component
class TabSecurityComponent {
    fun hasPermission(user: UserDomain, id: Long): Boolean {
        return true
    }
}
