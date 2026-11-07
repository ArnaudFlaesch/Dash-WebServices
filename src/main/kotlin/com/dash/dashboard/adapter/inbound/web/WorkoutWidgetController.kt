package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.workout.AddWorkoutTypeRequest
import com.dash.dashboard.adapter.inbound.web.request.workout.CreateWorkoutSessionRequest
import com.dash.dashboard.adapter.inbound.web.request.workout.UpdateWorkoutExerciseRequest
import com.dash.dashboard.application.port.inbound.WorkoutWidgetUseCase
import com.dash.dashboard.domain.model.workout.*
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/workoutWidget")
class WorkoutWidgetController(
    private val workoutWidgetUseCase: WorkoutWidgetUseCase
) {
    @GetMapping("/workoutTypes")
    fun getWorkoutTypes(): List<WorkoutType> = workoutWidgetUseCase.getWorkoutTypes()

    @GetMapping("/workoutSessions")
    fun getWorkoutsSessions(
        @RequestParam("dateIntervalStart")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        dateIntervalStart: LocalDate,
        @RequestParam("dateIntervalEnd")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        dateIntervalEnd: LocalDate
    ): List<WorkoutSession> = workoutWidgetUseCase.getWorkoutSessions(dateIntervalStart, dateIntervalEnd)

    @GetMapping("/workoutExercises")
    fun getWorkoutsExercisesByWorkoutSessionId(
        @RequestParam workoutSessionId: Int
    ): List<WorkoutExercise> = workoutWidgetUseCase.getWorkoutExercises(workoutSessionId)

    @GetMapping("/workoutStatsByPeriod")
    fun getWorkoutStatsByPeriod(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) dateIntervalStart: LocalDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) dateIntervalEnd: LocalDate
    ): List<WorkoutStatsByInterval> = workoutWidgetUseCase.getWorkoutStatsByPeriod(dateIntervalStart, dateIntervalEnd)

    @GetMapping("/workoutStatsByMonth")
    fun workoutStatsByMonth(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) dateIntervalStart: LocalDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) dateIntervalEnd: LocalDate
    ): List<WorkoutStatsByMonth> = workoutWidgetUseCase.getWorkoutStatsByMonth(dateIntervalStart, dateIntervalEnd)

    @PostMapping("/updateWorkoutExercise")
    fun updateWorkoutExercise(
        @RequestBody updateWorkoutExerciseRequest: UpdateWorkoutExerciseRequest
    ): WorkoutExercise =
        workoutWidgetUseCase.updateWorkoutExercise(
            updateWorkoutExerciseRequest.workoutSessionId,
            updateWorkoutExerciseRequest.workoutTypeId,
            updateWorkoutExerciseRequest.numberOfReps
        )

    @PostMapping("/addWorkoutType")
    fun addWorkoutType(
        @RequestBody addWorkoutTypeRequest: AddWorkoutTypeRequest
    ): WorkoutType = workoutWidgetUseCase.addWorkoutType(addWorkoutTypeRequest.workoutType)

    @PostMapping("/createWorkoutSession")
    fun createWorkoutSession(
        @RequestBody createWorkoutSessionRequest: CreateWorkoutSessionRequest
    ): WorkoutSession = workoutWidgetUseCase.createWorkoutSession(createWorkoutSessionRequest.workoutDate)
}
