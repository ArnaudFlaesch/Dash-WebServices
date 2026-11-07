package com.dash.dashboard.adapter.outbound.persistence.workout

import com.dash.dashboard.domain.model.workout.WorkoutType
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
    val id: Int,
    @Column(name = "name")
    val name: String,
    @ManyToOne(optional = true)
    @JoinColumn(name = "userId")
    val user: UserEntity
) {
    fun toDomain(): WorkoutType =
        WorkoutType(
            id = this.id,
            name = this.name,
            userId = this.user.id
        )
}
