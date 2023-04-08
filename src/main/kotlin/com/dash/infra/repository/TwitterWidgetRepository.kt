package com.dash.infra.repository

import com.dash.infra.entity.twitterwidget.FollowedUserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TwitterWidgetRepository : JpaRepository<FollowedUserEntity, Int> {

    @Query("SELECT * FROM followed_user WHERE user_handle ILIKE %:searchParam% AND user_id = :userId", nativeQuery = true)
    fun searchFollowedUsers(searchParam: String, pageRequest: Pageable, userId: Int): Page<FollowedUserEntity>
}
