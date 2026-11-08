package com.dash.dashboard.application.port.outbound

import com.dash.dashboard.domain.model.twitter.FollowedUser
import com.dash.shared.domain.model.Page

interface FollowedUserPersistencePort {
    /** Returns a page of the user's followed users whose handle contains [searchParam], sorted by handle. */
    fun getFollowedUsers(
        searchParam: String,
        pageNumber: Int,
        pageSize: Int,
        userId: Int
    ): Page<FollowedUser>

    fun addFollowedUser(
        followedUserHandle: String,
        userId: Int
    ): FollowedUser

    fun deleteFollowedUser(followedUserId: Int)

    fun isFollowedUserOwnedBy(
        followedUserId: Int,
        userId: Int
    ): Boolean
}
