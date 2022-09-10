package com.dash.controller

import com.dash.controller.requests.workoutWidget.UpdateWorkoutExercisePayload
import com.dash.controller.requests.workoutWidget.AddWorkoutTypePayload
import com.dash.controller.requests.workoutWidget.CreateWorkoutSessionPayload
import com.dash.model.workoutwidget.WorkoutExercise
import com.dash.model.workoutwidget.WorkoutSession
import com.dash.model.workoutwidget.WorkoutType
import com.dash.service.WorkoutWidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/workoutWidget")
class WorkoutWidgetController {

    @Autowired
    private lateinit var workoutWidgetService: WorkoutWidgetService

    @GetMapping("/workoutSessions")
    fun getWorkoutsSessions(): List<WorkoutSession> = (workoutWidgetService.getWorkoutSessions())

    @GetMapping("/workoutTypes")
    fun getWorkoutTypes(): List<WorkoutType> = (workoutWidgetService.getWorkoutTypes())

    @GetMapping("/workoutExercises")
    fun getWorkoutsExercisesByWorkoutSessionId(@RequestParam("workoutSessionId") workoutSessionId: Int): List<WorkoutExercise> =
        workoutWidgetService.getWorkoutsExercisesByWorkoutSessionId(workoutSessionId)

    @PostMapping("/updateWorkoutExercise")
    fun updateWorkoutExercise(@RequestBody updateWorkoutExercisePayload: UpdateWorkoutExercisePayload): WorkoutExercise =
        workoutWidgetService.updateWorkoutExercise(
            updateWorkoutExercisePayload.workoutSessionId,
            updateWorkoutExercisePayload.workoutTypeId,
            updateWorkoutExercisePayload.numberOfReps
        )

    @PostMapping("/addWorkoutType")
    fun addWorkoutType(@RequestBody addWorkoutTypePayload: AddWorkoutTypePayload): WorkoutType =
        workoutWidgetService.addWorkoutType(addWorkoutTypePayload.workoutType)

    @PostMapping("/createWorkoutSession")
    fun createWorkoutSession(@RequestBody createWorkoutSessionPayload: CreateWorkoutSessionPayload): WorkoutSession =
        workoutWidgetService.createWorkoutSession(createWorkoutSessionPayload.workoutDate)
}
