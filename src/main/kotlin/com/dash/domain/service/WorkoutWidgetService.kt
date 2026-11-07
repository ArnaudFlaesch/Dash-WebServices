package com.dash.domain.service

import com.common.app.security.SecurityConditions
import com.common.domain.service.UserService
import com.dash.domain.model.workoutwidget.*
import com.dash.infra.adapter.WorkoutWidgetPersistenceAdapter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class WorkoutWidgetService(
    private val workoutWidgetPersistenceAdapter: WorkoutWidgetPersistenceAdapter,
    private val userService: UserService
) {
    fun getWorkoutTypes(): List<WorkoutTypeDomain> =
        workoutWidgetPersistenceAdapter.getWorkoutTypes(userService.getCurrentAuthenticatedUserId())

    fun getWorkoutSessions(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate
    ): List<WorkoutSessionDomain> =
        workoutWidgetPersistenceAdapter.getWorkoutSessions(
            dateIntervalStart,
            dateIntervalEnd,
            userService.getCurrentAuthenticatedUserId()
        )

    fun getWorkoutStatsByPeriod(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate
    ): List<WorkoutStatsByIntervalDomain> =
        workoutWidgetPersistenceAdapter.getWorkoutStatsByPeriod(
            dateIntervalStart,
            dateIntervalEnd,
            userService.getCurrentAuthenticatedUserId()
        )

    fun getWorkoutStatsByMonth(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate
    ): List<WorkoutStatsByMonthDomain> =
        workoutWidgetPersistenceAdapter.getWorkoutStatsByMonth(
            dateIntervalStart,
            dateIntervalEnd,
            userService.getCurrentAuthenticatedUserId()
        )

    fun addWorkoutType(workoutType: String): WorkoutTypeDomain =
        workoutWidgetPersistenceAdapter.addWorkoutType(workoutType, userService.getCurrentAuthenticatedUserId())

    @PreAuthorize(SecurityConditions.DOES_WORKOUT_SESSION_BELONG_TO_AUTHENTICATED_USER)
    fun getWorkoutsExercisesByWorkoutSessionId(workoutSessionId: Int): List<WorkoutExerciseDomain> =
        workoutWidgetPersistenceAdapter.getWorkoutExercises(workoutSessionId)

    @PreAuthorize(SecurityConditions.DOES_WORKOUT_EXERCISE_BELONG_TO_AUTHENTICATED_USER)
    fun updateWorkoutExercise(
        workoutSessionId: Int,
        workoutTypeId: Int,
        numberOfReps: Int
    ): WorkoutExerciseDomain =
        workoutWidgetPersistenceAdapter.saveWorkoutExercise(
            WorkoutExerciseDomain(workoutSessionId, workoutTypeId, numberOfReps)
        )

    fun createWorkoutSession(workoutDate: LocalDate): WorkoutSessionDomain =
        workoutWidgetPersistenceAdapter.createWorkoutSession(workoutDate, userService.getCurrentAuthenticatedUserId())
}
