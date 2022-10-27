package com.dash.app.controller

import com.dash.app.controller.requests.workoutWidget.AddWorkoutTypePayload
import com.dash.app.controller.requests.workoutWidget.CreateWorkoutSessionPayload
import com.dash.app.controller.requests.workoutWidget.UpdateWorkoutExercisePayload
import com.dash.domain.model.workoutwidget.WorkoutExerciseDomain
import com.dash.domain.model.workoutwidget.WorkoutSessionDomain
import com.dash.domain.model.workoutwidget.WorkoutTypeDomain
import com.dash.domain.service.WorkoutWidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/workoutWidget")
class WorkoutWidgetController {

    @Autowired
    private lateinit var workoutWidgetService: WorkoutWidgetService

    @GetMapping("/workoutSessions")
    fun getWorkoutsSessions(@RequestParam("userId") userId: Int): List<WorkoutSessionDomain> =
        workoutWidgetService.getWorkoutSessions(userId)

    @GetMapping("/workoutTypes")
    fun getWorkoutTypes(@RequestParam("userId") userId: Int): List<WorkoutTypeDomain> =
        workoutWidgetService.getWorkoutTypes(userId)

    @GetMapping("/workoutExercises")
    fun getWorkoutsExercisesByWorkoutSessionId(@RequestParam("workoutSessionId") workoutSessionId: Int): List<WorkoutExerciseDomain> =
        workoutWidgetService.getWorkoutsExercisesByWorkoutSessionId(workoutSessionId)

    @PostMapping("/updateWorkoutExercise")
    fun updateWorkoutExercise(@RequestBody updateWorkoutExercisePayload: UpdateWorkoutExercisePayload): WorkoutExerciseDomain =
        workoutWidgetService.updateWorkoutExercise(
            updateWorkoutExercisePayload.workoutSessionId,
            updateWorkoutExercisePayload.workoutTypeId,
            updateWorkoutExercisePayload.numberOfReps
        )

    @PostMapping("/addWorkoutType")
    fun addWorkoutType(@RequestBody addWorkoutTypePayload: AddWorkoutTypePayload): WorkoutTypeDomain =
        workoutWidgetService.addWorkoutType(addWorkoutTypePayload.workoutType, addWorkoutTypePayload.userId)

    @PostMapping("/createWorkoutSession")
    fun createWorkoutSession(@RequestBody createWorkoutSessionPayload: CreateWorkoutSessionPayload): WorkoutSessionDomain =
        workoutWidgetService.createWorkoutSession(createWorkoutSessionPayload.workoutDate, createWorkoutSessionPayload.userId)
}
