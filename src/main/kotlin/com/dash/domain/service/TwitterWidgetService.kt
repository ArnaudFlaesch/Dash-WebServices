package com.dash.domain.service

import com.common.domain.service.UserService
import com.dash.domain.model.twitterwidget.FollowedUser
import com.dash.infra.adapter.TwitterWidgetAdapter
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class TwitterWidgetService(
    private val twitterWidgetAdapter: TwitterWidgetAdapter,
    private val userService: UserService
) {
    fun getFollowedUsers(
        searchParam: String,
        pageNumber: Int,
        pageSize: Int
    ): Page<FollowedUser> =
        twitterWidgetAdapter.getFollowedUsers(
            searchParam,
            pageNumber,
            pageSize,
            userService.getCurrentAuthenticatedUserId()
        )

    fun addFollowedUser(followedUserHandle: String): FollowedUser =
        twitterWidgetAdapter.addFollowedUser(followedUserHandle, userService.getCurrentAuthenticatedUserId())

    fun deleteFollowedUser(followedUserId: Int) = twitterWidgetAdapter.deleteFollowedUser(followedUserId)
}
