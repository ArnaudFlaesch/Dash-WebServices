package com.dash.domain.service

import com.common.domain.service.UserService
import com.dash.domain.model.twitterwidget.FollowedUser
import com.dash.infra.adapter.TwitterWidgetAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TwitterWidgetService {

    @Autowired
    private lateinit var twitterWidgetAdapter: TwitterWidgetAdapter

    @Autowired
    private lateinit var userService: UserService

    fun getFollowedUsers(searchParam: String): List<FollowedUser> {
        val authenticatedUserId = userService.getCurrentAuthenticatedUserId()
        return twitterWidgetAdapter.getFollowedUsers(searchParam, authenticatedUserId)
    }

    fun addFollowedUser(followedUserHandle: String): FollowedUser {
        val authenticatedUserId = userService.getCurrentAuthenticatedUserId()
        return twitterWidgetAdapter.addFollowedUser(followedUserHandle, authenticatedUserId)
    }

    fun deleteFollowedUser(followedUserId: Int) = twitterWidgetAdapter.deleteFollowedUser(followedUserId)
}
