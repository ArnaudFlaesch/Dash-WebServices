package com.dash.dashboard.adapter.outbound.persistence.workout

import com.dash.dashboard.domain.model.workout.WorkoutStatsByMonth
import java.time.LocalDate

data class WorkoutStatsByMonthEntity(
    val totalNumberOfReps: Long,
    val workoutTypeId: Int,
    val monthPeriod: Number,
    val yearPeriod: Number,
    val workoutTypeName: String
) {
    constructor() : this(0, 0, 0.0, 0.0, "")

    fun toDomain(): WorkoutStatsByMonth =
        WorkoutStatsByMonth(
            totalNumberOfReps = this.totalNumberOfReps,
            workoutTypeId = this.workoutTypeId,
            monthPeriod = LocalDate.of(yearPeriod.toInt(), monthPeriod.toInt(), 1),
            workoutTypeName = this.workoutTypeName
        )
}
