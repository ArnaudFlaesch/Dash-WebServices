package com.dash.infra.entity.workoutwidget

import com.dash.infra.entity.UserEntity
import java.time.LocalDate
import javax.persistence.*

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
)
