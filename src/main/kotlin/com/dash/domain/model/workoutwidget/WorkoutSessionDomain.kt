package com.dash.domain.model.workoutwidget

import java.time.LocalDate

data class WorkoutSessionDomain(
    val id: Int,
    val workoutDate: LocalDate,
    val userId: Int
)
