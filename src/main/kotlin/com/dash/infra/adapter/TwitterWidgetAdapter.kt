package com.dash.infra.adapter

import com.common.infra.repository.UserRepository
import com.dash.domain.model.twitterwidget.FollowedUser
import com.dash.infra.entity.twitterwidget.FollowedUserEntity
import com.dash.infra.repository.TwitterWidgetRepository
import org.springframework.stereotype.Component

@Component
class TwitterWidgetAdapter(
    private val twitterWidgetRepository: TwitterWidgetRepository,
    private val userRepository: UserRepository
) {

    fun getFollowedUsers(searchParam: String, userId: Int): List<FollowedUser> =
        twitterWidgetRepository.searchFollowedUsers(searchParam, userId).map(FollowedUserEntity::toDomain)

    fun addFollowedUser(followedUserHandle: String, userId: Int): FollowedUser {
        return twitterWidgetRepository.save(
            FollowedUserEntity(
                id = 0,
                userHandle = followedUserHandle,
                user = userRepository.getReferenceById(userId)
            )
        ).toDomain()
    }

    fun deleteFollowedUser(followedUserId: Int) =
        twitterWidgetRepository.delete(twitterWidgetRepository.getReferenceById(followedUserId))
}
