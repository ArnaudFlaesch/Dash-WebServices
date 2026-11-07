package com.dash.dashboard.adapter.outbound.persistence.twitter

import com.dash.dashboard.domain.model.twitter.FollowedUser
import com.dash.shared.adapter.outbound.persistence.user.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "followed_user")
class FollowedUserEntity(
    @Id
    @SequenceGenerator(
        name = "followed-user-seq-gen",
        sequenceName = "followed_user_id_seq",
        initialValue = 1,
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "followed-user-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,
    @Column(name = "user_handle")
    val userHandle: String,
    @ManyToOne(optional = true)
    @JoinColumn(name = "userId")
    val user: UserEntity
) {
    fun toDomain(): FollowedUser = FollowedUser(id = id, userHandle = userHandle, userId = user.id)
}
