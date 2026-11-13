package com.dash.dashboard.adapter.outbound.persistence.workout

import com.dash.shared.adapter.outbound.persistence.user.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "workout_type")
class WorkoutTypeEntity(
    @Id
    @SequenceGenerator(
        name = "workout-type-seq-gen",
        sequenceName = "workout_type_id_seq",
        initialValue = 1,
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workout-type-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    var id: Int,
    @Column(name = "name")
    var name: String,
    @ManyToOne(optional = true)
    @JoinColumn(name = "userId")
    var user: UserEntity
)
