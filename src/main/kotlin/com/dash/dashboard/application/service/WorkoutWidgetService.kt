package com.dash.dashboard.application.service

import com.dash.dashboard.application.port.inbound.WorkoutWidgetUseCase
import com.dash.dashboard.application.port.outbound.WorkoutPersistencePort
import com.dash.dashboard.application.security.DashboardSecurityConditions
import com.dash.dashboard.domain.model.workout.WorkoutExercise
import com.dash.dashboard.domain.model.workout.WorkoutSession
import com.dash.dashboard.domain.model.workout.WorkoutStatsByInterval
import com.dash.dashboard.domain.model.workout.WorkoutStatsByMonth
import com.dash.dashboard.domain.model.workout.WorkoutType
import com.dash.shared.application.port.outbound.CurrentUserPort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class WorkoutWidgetService(
    private val workoutPersistencePort: WorkoutPersistencePort,
    private val currentUserPort: CurrentUserPort
) : WorkoutWidgetUseCase {
    override fun getWorkoutTypes(): List<WorkoutType> =
        workoutPersistencePort.getWorkoutTypes(currentUserPort.currentUserId())

    override fun getWorkoutSessions(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate
    ): List<WorkoutSession> =
        workoutPersistencePort.getWorkoutSessions(dateIntervalStart, dateIntervalEnd, currentUserPort.currentUserId())

    override fun getWorkoutStatsByPeriod(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate
    ): List<WorkoutStatsByInterval> =
        workoutPersistencePort.getWorkoutStatsByPeriod(
            dateIntervalStart,
            dateIntervalEnd,
            currentUserPort.currentUserId()
        )

    override fun getWorkoutStatsByMonth(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate
    ): List<WorkoutStatsByMonth> =
        workoutPersistencePort.getWorkoutStatsByMonth(
            dateIntervalStart,
            dateIntervalEnd,
            currentUserPort.currentUserId()
        )

    override fun addWorkoutType(workoutType: String): WorkoutType =
        workoutPersistencePort.addWorkoutType(workoutType, currentUserPort.currentUserId())

    @PreAuthorize(DashboardSecurityConditions.DOES_WORKOUT_SESSION_BELONG_TO_AUTHENTICATED_USER)
    override fun getWorkoutExercises(workoutSessionId: Int): List<WorkoutExercise> =
        workoutPersistencePort.getWorkoutExercises(workoutSessionId)

    @PreAuthorize(DashboardSecurityConditions.DOES_WORKOUT_EXERCISE_BELONG_TO_AUTHENTICATED_USER)
    override fun updateWorkoutExercise(
        workoutSessionId: Int,
        workoutTypeId: Int,
        numberOfReps: Int
    ): WorkoutExercise =
        workoutPersistencePort.saveWorkoutExercise(WorkoutExercise(workoutSessionId, workoutTypeId, numberOfReps))

    override fun createWorkoutSession(workoutDate: LocalDate): WorkoutSession =
        workoutPersistencePort.createWorkoutSession(workoutDate, currentUserPort.currentUserId())
}
