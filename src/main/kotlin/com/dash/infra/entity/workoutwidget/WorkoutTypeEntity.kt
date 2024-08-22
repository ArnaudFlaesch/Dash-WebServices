package com.dash.infra.entity.workoutwidget

import com.common.infra.entity.UserEntity
import com.dash.domain.model.workoutwidget.WorkoutTypeDomain
import jakarta.persistence.*

@Entity
@Table(name = "workout_type")
data class WorkoutTypeEntity(
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
    fun toDomain(): WorkoutTypeDomain =
        WorkoutTypeDomain(
            id = this.id,
            name = this.name,
            userId = this.user.id
        )
}
