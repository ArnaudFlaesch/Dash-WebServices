package com.dash.model.workoutwidget

import java.time.LocalDate
import javax.persistence.*

@Entity
data class WorkoutSession(
    @Id
    @SequenceGenerator(name = "workout-session-seq-gen", sequenceName = "workout_session_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workout-session-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,

    @Column(name = "workoutDate")
    val workoutDate: LocalDate
)
