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

    fun getFollowedUsers(searchParam: String, pageNumber: Int, pageSize: Int): Page<FollowedUser> {
        val authenticatedUserId = userService.getCurrentAuthenticatedUser().id
        return twitterWidgetAdapter.getFollowedUsers(searchParam, pageNumber, pageSize, authenticatedUserId)
    }

    fun addFollowedUser(followedUserHandle: String): FollowedUser {
        val authenticatedUserId = userService.getCurrentAuthenticatedUser().id
        return twitterWidgetAdapter.addFollowedUser(followedUserHandle, authenticatedUserId)
    }

    fun deleteFollowedUser(followedUserId: Int) = twitterWidgetAdapter.deleteFollowedUser(followedUserId)
}
