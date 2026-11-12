package com.dash.dashboard.adapter.outbound.persistence.workout

data class WorkoutStatsByMonthEntity(
    val totalNumberOfReps: Long,
    val workoutTypeId: Int,
    val monthPeriod: Number,
    val yearPeriod: Number,
    val workoutTypeName: String
) {
    constructor() : this(0, 0, 0.0, 0.0, "")
}
