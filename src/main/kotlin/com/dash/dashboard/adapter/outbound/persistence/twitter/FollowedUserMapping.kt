package com.dash.dashboard.adapter.outbound.persistence.twitter

import com.dash.dashboard.domain.model.twitter.FollowedUser
import com.dash.shared.adapter.outbound.persistence.user.UserEntity
import jakarta.persistence.*

fun FollowedUserEntity.toDomain(): FollowedUser = FollowedUser(id = id, userHandle = userHandle, userId = user.id)
