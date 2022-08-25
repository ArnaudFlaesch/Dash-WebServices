package com.dash.controller.requests.workoutWidget

import java.time.LocalDate

data class CreateWorkoutSessionPayload(
    val workoutDate: LocalDate
)
