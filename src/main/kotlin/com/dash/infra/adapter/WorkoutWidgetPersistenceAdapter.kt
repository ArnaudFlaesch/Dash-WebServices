package com.dash.infra.adapter

import com.common.infra.repository.UserRepository
import com.dash.domain.model.workoutwidget.*
import com.dash.infra.entity.workoutwidget.*
import com.dash.infra.repository.WorkoutExerciseRepository
import com.dash.infra.repository.WorkoutSessionRepository
import com.dash.infra.repository.WorkoutTypeRepository
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class WorkoutWidgetPersistenceAdapter(
    private val workoutTypeRepository: WorkoutTypeRepository,
    private val workoutExerciseRepository: WorkoutExerciseRepository,
    private val workoutSessionRepository: WorkoutSessionRepository,
    private val userRepository: UserRepository
) {
    fun getWorkoutTypes(userId: Int): List<WorkoutTypeDomain> =
        workoutTypeRepository.findByUserIdOrderByNameAsc(userId).map(WorkoutTypeEntity::toDomain)

    fun getWorkoutSessions(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate,
        userId: Int
    ): List<WorkoutSessionDomain> =
        workoutSessionRepository
            .findByUserIdAndWorkoutDateBetweenOrderByWorkoutDateAsc(userId, dateIntervalStart, dateIntervalEnd)
            .map(WorkoutSessionEntity::toDomain)

    fun getWorkoutStatsByPeriod(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate,
        userId: Int
    ): List<WorkoutStatsByIntervalDomain> =
        workoutExerciseRepository
            .getWorkoutStatsByInterval(dateIntervalStart, dateIntervalEnd, userId)
            .map(WorkoutStatsByIntervalEntity::toDomain)

    fun getWorkoutStatsByMonth(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate,
        userId: Int
    ): List<WorkoutStatsByMonthDomain> =
        workoutExerciseRepository
            .getWorkoutStatsByMonth(dateIntervalStart, dateIntervalEnd, userId)
            .map(WorkoutStatsByMonthEntity::toDomain)

    fun getWorkoutExercises(workoutSessionId: Int): List<WorkoutExerciseDomain> =
        workoutExerciseRepository
            .findAllByWorkoutExerciseIdWorkoutSessionId(workoutSessionId)
            .map(WorkoutExerciseEntity::toDomain)

    fun addWorkoutType(
        name: String,
        userId: Int
    ): WorkoutTypeDomain =
        WorkoutTypeEntity(id = 0, name = name, user = userRepository.getReferenceById(userId))
            .let(workoutTypeRepository::save)
            .let(WorkoutTypeEntity::toDomain)

    fun saveWorkoutExercise(workoutExercise: WorkoutExerciseDomain): WorkoutExerciseDomain =
        WorkoutExerciseEntity(
            workoutExerciseId =
                WorkoutExerciseEntityId(workoutExercise.workoutSessionId, workoutExercise.workoutTypeId),
            numberOfReps = workoutExercise.numberOfReps
        ).let(workoutExerciseRepository::save)
            .let(WorkoutExerciseEntity::toDomain)

    fun createWorkoutSession(
        workoutDate: LocalDate,
        userId: Int
    ): WorkoutSessionDomain =
        WorkoutSessionEntity(id = 0, workoutDate = workoutDate, user = userRepository.getReferenceById(userId))
            .let(workoutSessionRepository::save)
            .let(WorkoutSessionEntity::toDomain)
}
