package com.dash.dashboard.adapter.outbound.persistence.workout

import com.dash.dashboard.domain.model.workout.WorkoutExercise
import com.dash.dashboard.domain.model.workout.WorkoutSession
import com.dash.dashboard.domain.model.workout.WorkoutStatsByInterval
import com.dash.dashboard.domain.model.workout.WorkoutStatsByMonth
import com.dash.dashboard.domain.model.workout.WorkoutType
import com.dash.shared.adapter.outbound.persistence.user.UserEntity
import jakarta.persistence.*
import java.time.LocalDate

fun WorkoutExerciseEntity.toDomain(): WorkoutExercise =
    WorkoutExercise(
        workoutSessionId = this.workoutExerciseId.workoutSessionId,
        workoutTypeId = this.workoutExerciseId.workoutTypeId,
        numberOfReps = this.numberOfReps
    )

fun WorkoutSessionEntity.toDomain(): WorkoutSession =
    WorkoutSession(
        id = this.id,
        workoutDate = this.workoutDate,
        userId = this.user.id
    )

fun WorkoutStatsByIntervalEntity.toDomain(): WorkoutStatsByInterval =
    WorkoutStatsByInterval(
        totalNumberOfReps = this.totalNumberOfReps,
        workoutTypeName = this.workoutTypeName
    )

fun WorkoutStatsByMonthEntity.toDomain(): WorkoutStatsByMonth =
    WorkoutStatsByMonth(
        totalNumberOfReps = this.totalNumberOfReps,
        workoutTypeId = this.workoutTypeId,
        monthPeriod = LocalDate.of(yearPeriod.toInt(), monthPeriod.toInt(), 1),
        workoutTypeName = this.workoutTypeName
    )

fun WorkoutTypeEntity.toDomain(): WorkoutType =
    WorkoutType(
        id = this.id,
        name = this.name,
        userId = this.user.id
    )
