package com.dash.dashboard.adapter.outbound.persistence.workout

import com.dash.dashboard.domain.model.workout.WorkoutSession
import com.dash.shared.adapter.outbound.persistence.user.UserEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "workout_session")
class WorkoutSessionEntity(
    @Id
    @SequenceGenerator(
        name = "workout-session-seq-gen",
        sequenceName = "workout_session_id_seq",
        initialValue = 1,
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workout-session-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,
    @Column(name = "workoutDate")
    val workoutDate: LocalDate,
    @ManyToOne(optional = true)
    @JoinColumn(name = "userId")
    val user: UserEntity
) {
    fun toDomain(): WorkoutSession =
        WorkoutSession(
            id = this.id,
            workoutDate = this.workoutDate,
            userId = this.user.id
        )
}
