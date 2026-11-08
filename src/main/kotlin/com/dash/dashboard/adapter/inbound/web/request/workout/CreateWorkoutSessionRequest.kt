package com.dash.dashboard.adapter.inbound.web.request.workout

import java.time.LocalDate

data class CreateWorkoutSessionRequest(
    val workoutDate: LocalDate
)
