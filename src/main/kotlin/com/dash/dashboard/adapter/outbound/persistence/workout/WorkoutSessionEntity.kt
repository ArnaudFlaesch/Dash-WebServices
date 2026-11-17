package com.dash.dashboard.adapter.outbound.persistence.workout

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
    var id: Int,
    @Column(name = "workoutDate")
    var workoutDate: LocalDate,
    @ManyToOne(optional = true)
    @JoinColumn(name = "userId")
    var user: UserEntity
)
