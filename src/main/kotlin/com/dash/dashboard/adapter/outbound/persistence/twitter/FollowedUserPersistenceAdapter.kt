package com.dash.dashboard.adapter.outbound.persistence.twitter

import com.dash.dashboard.application.port.outbound.FollowedUserPersistencePort
import com.dash.dashboard.domain.model.twitter.FollowedUser
import com.dash.shared.adapter.outbound.persistence.toDomainPage
import com.dash.shared.adapter.outbound.persistence.user.UserRepository
import com.dash.shared.domain.model.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class FollowedUserPersistenceAdapter(
    private val followedUserRepository: FollowedUserRepository,
    private val userRepository: UserRepository
) : FollowedUserPersistencePort {
    override fun getFollowedUsers(
        searchParam: String,
        pageNumber: Int,
        pageSize: Int,
        userId: Int
    ): Page<FollowedUser> =
        followedUserRepository
            .searchFollowedUsers(searchParam, userId, PageRequest.of(pageNumber, pageSize))
            .toDomainPage(FollowedUserEntity::toDomain)

    override fun addFollowedUser(
        followedUserHandle: String,
        userId: Int
    ): FollowedUser =
        FollowedUserEntity(
            id = 0,
            userHandle = followedUserHandle,
            user = userRepository.getReferenceById(userId)
        ).let(followedUserRepository::save)
            .let(FollowedUserEntity::toDomain)

    override fun deleteFollowedUser(followedUserId: Int) {
        followedUserRepository.deleteById(followedUserId)
    }

    override fun isFollowedUserOwnedBy(
        followedUserId: Int,
        userId: Int
    ): Boolean = followedUserRepository.existsByIdAndUserId(followedUserId, userId)
}
