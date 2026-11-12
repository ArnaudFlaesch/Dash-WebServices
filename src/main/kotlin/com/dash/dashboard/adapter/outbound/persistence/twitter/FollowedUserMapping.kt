package com.dash.dashboard.adapter.outbound.persistence.twitter

import com.dash.dashboard.domain.model.twitter.FollowedUser

fun FollowedUserEntity.toDomain(): FollowedUser = FollowedUser(id = id, userHandle = userHandle, userId = user.id)
