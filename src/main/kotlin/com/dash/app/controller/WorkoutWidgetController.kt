package com.dash.app.controller

import com.dash.app.controller.requests.workoutWidget.AddWorkoutTypePayload
import com.dash.app.controller.requests.workoutWidget.CreateWorkoutSessionPayload
import com.dash.app.controller.requests.workoutWidget.UpdateWorkoutExercisePayload
import com.dash.domain.model.workoutwidget.WorkoutExerciseDomain
import com.dash.domain.model.workoutwidget.WorkoutSessionDomain
import com.dash.domain.model.workoutwidget.WorkoutStatsByIntervalDomain
import com.dash.domain.model.workoutwidget.WorkoutTypeDomain
import com.dash.domain.service.WorkoutWidgetService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/workoutWidget")
class WorkoutWidgetController(
    private val workoutWidgetService: WorkoutWidgetService
) {

    @GetMapping("/workoutTypes")
    fun getWorkoutTypes(): List<WorkoutTypeDomain> =
        workoutWidgetService.getWorkoutTypes()

    @GetMapping("/workoutSessions")
    fun getWorkoutsSessions(
        @RequestParam("dateIntervalStart")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        dateIntervalStart: LocalDate,
        @RequestParam("dateIntervalEnd")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        dateIntervalEnd: LocalDate
    ): List<WorkoutSessionDomain> =
        workoutWidgetService.getWorkoutSessions(dateIntervalStart, dateIntervalEnd)

    @GetMapping("/workoutExercises")
    fun getWorkoutsExercisesByWorkoutSessionId(@RequestParam("workoutSessionId") workoutSessionId: Int): List<WorkoutExerciseDomain> =
        workoutWidgetService.getWorkoutsExercisesByWorkoutSessionId(workoutSessionId)

    @GetMapping("/workoutStatsByPeriod")
    fun getWorkoutStatsByPeriod(
        @RequestParam("dateIntervalStart") dateIntervalStart: LocalDate,
        @RequestParam("dateIntervalEnd") dateIntervalEnd: LocalDate
    ): List<WorkoutStatsByIntervalDomain> =
        workoutWidgetService.getWorkoutStatsByPeriod(dateIntervalStart, dateIntervalEnd)

    @PostMapping("/updateWorkoutExercise")
    fun updateWorkoutExercise(@RequestBody updateWorkoutExercisePayload: UpdateWorkoutExercisePayload): WorkoutExerciseDomain =
        workoutWidgetService.updateWorkoutExercise(
            updateWorkoutExercisePayload.workoutSessionId,
            updateWorkoutExercisePayload.workoutTypeId,
            updateWorkoutExercisePayload.numberOfReps
        )

    @PostMapping("/addWorkoutType")
    fun addWorkoutType(@RequestBody addWorkoutTypePayload: AddWorkoutTypePayload): WorkoutTypeDomain =
        workoutWidgetService.addWorkoutType(addWorkoutTypePayload.workoutType)

    @PostMapping("/createWorkoutSession")
    fun createWorkoutSession(@RequestBody createWorkoutSessionPayload: CreateWorkoutSessionPayload): WorkoutSessionDomain =
        workoutWidgetService.createWorkoutSession(createWorkoutSessionPayload.workoutDate)
}
