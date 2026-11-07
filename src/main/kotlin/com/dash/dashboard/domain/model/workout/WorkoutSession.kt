package com.dash.dashboard.domain.model.workout

import java.time.LocalDate

data class WorkoutSession(
    val id: Int,
    val workoutDate: LocalDate,
    val userId: Int
)
