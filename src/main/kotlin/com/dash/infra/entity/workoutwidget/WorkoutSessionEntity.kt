package com.dash.infra.entity.workoutwidget

import com.common.infra.entity.UserEntity
import com.dash.domain.model.workoutwidget.WorkoutSessionDomain
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "workout_session")
data class WorkoutSessionEntity(
    @Id
    @SequenceGenerator(name = "workout-session-seq-gen", sequenceName = "workout_session_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workout-session-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,
    @Column(name = "workoutDate")
    val workoutDate: LocalDate,
    @ManyToOne(optional = true)
    @JoinColumn(name = "userId")
    val user: UserEntity
) {
    fun toDomain(): WorkoutSessionDomain =
        WorkoutSessionDomain(
            id = this.id,
            workoutDate = this.workoutDate,
            userId = this.user.id
        )
}
