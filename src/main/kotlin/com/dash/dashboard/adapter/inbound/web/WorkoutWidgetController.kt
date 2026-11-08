package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.workout.AddWorkoutTypeRequest
import com.dash.dashboard.adapter.inbound.web.request.workout.CreateWorkoutSessionRequest
import com.dash.dashboard.adapter.inbound.web.request.workout.UpdateWorkoutExerciseRequest
import com.dash.dashboard.application.port.inbound.WorkoutWidgetUseCase
import com.dash.dashboard.domain.model.workout.*
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/workout")
class WorkoutWidgetController(
    private val workoutWidgetUseCase: WorkoutWidgetUseCase
) {
    @GetMapping("/types")
    fun getWorkoutTypes(): List<WorkoutType> = workoutWidgetUseCase.getWorkoutTypes()

    @PostMapping("/types")
    @ResponseStatus(HttpStatus.CREATED)
    fun addWorkoutType(
        @RequestBody addWorkoutTypeRequest: AddWorkoutTypeRequest
    ): WorkoutType = workoutWidgetUseCase.addWorkoutType(addWorkoutTypeRequest.workoutType)

    @GetMapping("/sessions")
    fun getWorkoutsSessions(
        @RequestParam("dateIntervalStart")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        dateIntervalStart: LocalDate,
        @RequestParam("dateIntervalEnd")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        dateIntervalEnd: LocalDate
    ): List<WorkoutSession> = workoutWidgetUseCase.getWorkoutSessions(dateIntervalStart, dateIntervalEnd)

    @PostMapping("/sessions")
    @ResponseStatus(HttpStatus.CREATED)
    fun createWorkoutSession(
        @RequestBody createWorkoutSessionRequest: CreateWorkoutSessionRequest
    ): WorkoutSession = workoutWidgetUseCase.createWorkoutSession(createWorkoutSessionRequest.workoutDate)

    @GetMapping("/sessions/{workoutSessionId}/exercises")
    fun getWorkoutsExercisesByWorkoutSessionId(
        @PathVariable workoutSessionId: Int
    ): List<WorkoutExercise> = workoutWidgetUseCase.getWorkoutExercises(workoutSessionId)

    @PutMapping("/sessions/{workoutSessionId}/exercises/{workoutTypeId}")
    fun updateWorkoutExercise(
        @PathVariable workoutSessionId: Int,
        @PathVariable workoutTypeId: Int,
        @RequestBody updateWorkoutExerciseRequest: UpdateWorkoutExerciseRequest
    ): WorkoutExercise =
        workoutWidgetUseCase.updateWorkoutExercise(
            workoutSessionId,
            workoutTypeId,
            updateWorkoutExerciseRequest.numberOfReps
        )

    @GetMapping("/stats/by-period")
    fun getWorkoutStatsByPeriod(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) dateIntervalStart: LocalDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) dateIntervalEnd: LocalDate
    ): List<WorkoutStatsByInterval> = workoutWidgetUseCase.getWorkoutStatsByPeriod(dateIntervalStart, dateIntervalEnd)

    @GetMapping("/stats/by-month")
    fun workoutStatsByMonth(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) dateIntervalStart: LocalDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) dateIntervalEnd: LocalDate
    ): List<WorkoutStatsByMonth> = workoutWidgetUseCase.getWorkoutStatsByMonth(dateIntervalStart, dateIntervalEnd)
}
