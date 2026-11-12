package com.dash.dashboard.application.service

import com.dash.dashboard.application.port.inbound.TwitterWidgetUseCase
import com.dash.dashboard.application.port.outbound.FollowedUserPersistencePort
import com.dash.dashboard.application.security.DashboardSecurityConditions
import com.dash.dashboard.domain.model.twitter.FollowedUser
import com.dash.shared.application.port.outbound.CurrentUserPort
import com.dash.shared.domain.model.Page
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class TwitterWidgetService(
    private val followedUserPersistencePort: FollowedUserPersistencePort,
    private val currentUserPort: CurrentUserPort
) : TwitterWidgetUseCase {
    override fun getFollowedUsers(
        searchParam: String,
        pageNumber: Int,
        pageSize: Int
    ): Page<FollowedUser> =
        followedUserPersistencePort.getFollowedUsers(
            searchParam,
            pageNumber,
            pageSize,
            currentUserPort.currentUserId()
        )

    override fun addFollowedUser(followedUserHandle: String): FollowedUser =
        followedUserPersistencePort.addFollowedUser(followedUserHandle, currentUserPort.currentUserId())

    @PreAuthorize(DashboardSecurityConditions.DOES_FOLLOWED_USER_BELONG_TO_AUTHENTICATED_USER)
    override fun deleteFollowedUser(followedUserId: Int) =
        followedUserPersistencePort.deleteFollowedUser(followedUserId)
}
