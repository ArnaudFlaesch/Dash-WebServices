package com.dash.dashboard.adapter.outbound.persistence.workout

import com.dash.dashboard.application.port.outbound.WorkoutPersistencePort
import com.dash.dashboard.domain.model.workout.*
import com.dash.shared.adapter.outbound.persistence.user.UserRepository
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class WorkoutPersistenceAdapter(
    private val workoutTypeRepository: WorkoutTypeRepository,
    private val workoutExerciseRepository: WorkoutExerciseRepository,
    private val workoutSessionRepository: WorkoutSessionRepository,
    private val userRepository: UserRepository
) : WorkoutPersistencePort {
    override fun getWorkoutTypes(userId: Int): List<WorkoutType> =
        workoutTypeRepository.findByUserIdOrderByNameAsc(userId).map(WorkoutTypeEntity::toDomain)

    override fun getWorkoutSessions(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate,
        userId: Int
    ): List<WorkoutSession> =
        workoutSessionRepository
            .findByUserIdAndWorkoutDateBetweenOrderByWorkoutDateAsc(userId, dateIntervalStart, dateIntervalEnd)
            .map(WorkoutSessionEntity::toDomain)

    override fun getWorkoutStatsByPeriod(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate,
        userId: Int
    ): List<WorkoutStatsByInterval> =
        workoutExerciseRepository
            .getWorkoutStatsByInterval(dateIntervalStart, dateIntervalEnd, userId)
            .map(WorkoutStatsByIntervalEntity::toDomain)

    override fun getWorkoutStatsByMonth(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate,
        userId: Int
    ): List<WorkoutStatsByMonth> =
        workoutExerciseRepository
            .getWorkoutStatsByMonth(dateIntervalStart, dateIntervalEnd, userId)
            .map(WorkoutStatsByMonthEntity::toDomain)

    override fun getWorkoutExercises(workoutSessionId: Int): List<WorkoutExercise> =
        workoutExerciseRepository
            .findAllByWorkoutExerciseIdWorkoutSessionId(workoutSessionId)
            .map(WorkoutExerciseEntity::toDomain)

    override fun addWorkoutType(
        name: String,
        userId: Int
    ): WorkoutType =
        WorkoutTypeEntity(id = 0, name = name, user = userRepository.getReferenceById(userId))
            .let(workoutTypeRepository::save)
            .let(WorkoutTypeEntity::toDomain)

    override fun saveWorkoutExercise(workoutExercise: WorkoutExercise): WorkoutExercise =
        WorkoutExerciseEntity(
            workoutExerciseId =
                WorkoutExerciseEntityId(workoutExercise.workoutSessionId, workoutExercise.workoutTypeId),
            numberOfReps = workoutExercise.numberOfReps
        ).let(workoutExerciseRepository::save)
            .let(WorkoutExerciseEntity::toDomain)

    override fun createWorkoutSession(
        workoutDate: LocalDate,
        userId: Int
    ): WorkoutSession =
        WorkoutSessionEntity(id = 0, workoutDate = workoutDate, user = userRepository.getReferenceById(userId))
            .let(workoutSessionRepository::save)
            .let(WorkoutSessionEntity::toDomain)

    override fun isWorkoutSessionOwnedBy(
        workoutSessionId: Int,
        userId: Int
    ): Boolean = workoutSessionRepository.existsByIdAndUserId(workoutSessionId, userId)

    override fun isWorkoutTypeOwnedBy(
        workoutTypeId: Int,
        userId: Int
    ): Boolean = workoutTypeRepository.existsByIdAndUserId(workoutTypeId, userId)
}
