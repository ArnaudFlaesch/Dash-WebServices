package com.dash.dashboard.application.port.inbound

import com.dash.dashboard.domain.model.twitter.FollowedUser
import com.dash.shared.domain.model.Page

interface TwitterWidgetUseCase {
    fun getFollowedUsers(
        searchParam: String,
        pageNumber: Int,
        pageSize: Int
    ): Page<FollowedUser>

    fun addFollowedUser(followedUserHandle: String): FollowedUser

    fun deleteFollowedUser(followedUserId: Int)
}
