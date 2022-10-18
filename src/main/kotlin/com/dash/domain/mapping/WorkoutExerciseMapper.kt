package com.dash.domain.mapping

import com.dash.domain.model.workoutwidget.WorkoutExerciseDomain
import com.dash.infra.entity.workoutwidget.WorkoutExerciseEntity
import org.springframework.stereotype.Component

@Component
class WorkoutExerciseMapper {

    fun mapEntityToDomain(workoutSessionEntity: WorkoutExerciseEntity): WorkoutExerciseDomain =
        WorkoutExerciseDomain(
            workoutSessionId = workoutSessionEntity.workoutExerciseId.workoutSessionId,
            workoutTypeId = workoutSessionEntity.workoutExerciseId.workoutTypeId,
            numberOfReps = workoutSessionEntity.numberOfReps
        )
}
