package com.dash.domain.service

import com.dash.domain.model.workoutwidget.WorkoutExercise
import com.dash.domain.model.workoutwidget.WorkoutExerciseId
import com.dash.domain.model.workoutwidget.WorkoutSession
import com.dash.domain.model.workoutwidget.WorkoutType
import com.dash.infra.repository.WorkoutExerciseRepository
import com.dash.infra.repository.WorkoutSessionRepository
import com.dash.infra.repository.WorkoutTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class WorkoutWidgetService {

    @Autowired
    private lateinit var workoutTypeRepository: WorkoutTypeRepository

    @Autowired
    private lateinit var workoutExerciseRepository: WorkoutExerciseRepository

    @Autowired
    private lateinit var workoutSessionRepository: WorkoutSessionRepository

    @Autowired
    private lateinit var userService: UserService

    fun getWorkoutTypes(): List<WorkoutType> = workoutTypeRepository.findAll()

    fun addWorkoutType(workoutType: String, userId: Int): WorkoutType =
        workoutTypeRepository.save(WorkoutType(0, workoutType, userService.getUserById(userId)))

    fun getWorkoutsExercisesByWorkoutSessionId(workoutSessionId: Int): List<WorkoutExercise> =
        workoutExerciseRepository.findAllByWorkoutSessionId(workoutSessionId)

    fun updateWorkoutExercise(workoutSessionId: Int, workoutTypeId: Int, numberOfReps: Int): WorkoutExercise =
        workoutExerciseRepository.save(WorkoutExercise(WorkoutExerciseId(workoutSessionId, workoutTypeId), numberOfReps))

    fun getWorkoutSessions(): List<WorkoutSession> = workoutSessionRepository.findAll()

    fun createWorkoutSession(workoutDate: LocalDate, userId: Int): WorkoutSession =
        workoutSessionRepository.save(WorkoutSession(0, workoutDate, userService.getUserById(userId)))
}
