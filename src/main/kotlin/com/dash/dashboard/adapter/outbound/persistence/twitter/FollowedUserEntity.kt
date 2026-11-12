package com.dash.dashboard.adapter.outbound.persistence.twitter

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
    var id: Int,
    @Column(name = "user_handle")
    var userHandle: String,
    @ManyToOne(optional = true)
    @JoinColumn(name = "userId")
    var user: UserEntity
)
