package com.dash.app.controller

import com.dash.app.controller.requests.workoutWidget.AddWorkoutTypePayload
import com.dash.app.controller.requests.workoutWidget.CreateWorkoutSessionPayload
import com.dash.app.controller.requests.workoutWidget.UpdateWorkoutExercisePayload
import com.dash.domain.model.workoutwidget.WorkoutExerciseDomain
import com.dash.domain.model.workoutwidget.WorkoutSessionDomain
import com.dash.domain.model.workoutwidget.WorkoutStatsByMonthDomain
import com.dash.domain.model.workoutwidget.WorkoutTypeDomain
import com.dash.domain.service.WorkoutWidgetService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/workoutWidget")
class WorkoutWidgetController(
    private val workoutWidgetService: WorkoutWidgetService
) {

    @GetMapping("/workoutSessions")
    fun getWorkoutsSessions(): List<WorkoutSessionDomain> =
        workoutWidgetService.getWorkoutSessions()

    @GetMapping("/workoutTypes")
    fun getWorkoutTypes(): List<WorkoutTypeDomain> =
        workoutWidgetService.getWorkoutTypes()

    @GetMapping("/workoutExercises")
    fun getWorkoutsExercisesByWorkoutSessionId(@RequestParam("workoutSessionId") workoutSessionId: Int): List<WorkoutExerciseDomain> =
        workoutWidgetService.getWorkoutsExercisesByWorkoutSessionId(workoutSessionId)

    @GetMapping("/workoutStatsByMonth")
    fun getWorkoutStatsByMonth(
        @RequestParam("dateMonth") dateMonth: LocalDate
    ): List<WorkoutStatsByMonthDomain> =
        workoutWidgetService.getWorkoutStatsByMonth(dateMonth)

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
