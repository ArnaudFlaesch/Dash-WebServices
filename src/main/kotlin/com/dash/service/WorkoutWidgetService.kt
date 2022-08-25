package com.dash.service

import com.dash.model.workoutwidget.WorkoutExercise
import com.dash.model.workoutwidget.WorkoutExerciseId
import com.dash.model.workoutwidget.WorkoutSession
import com.dash.model.workoutwidget.WorkoutType
import com.dash.repository.WorkoutExerciseRepository
import com.dash.repository.WorkoutSessionRepository
import com.dash.repository.WorkoutTypeRepository
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

    fun getWorkoutTypes(): List<WorkoutType> = workoutTypeRepository.findAll()

    fun addWorkoutType(workoutType: String): WorkoutType = workoutTypeRepository.save(WorkoutType(0, workoutType))

    fun getWorkoutsExercisesByWorkoutSessionId(workoutSessionId: Int): List<WorkoutExercise> =
        workoutExerciseRepository.findAllByWorkoutSessionId(workoutSessionId)

    fun createWorkoutExercise(workoutSessionId: Int, workoutTypeId: Int, numberOfReps: Int): WorkoutExercise =
        workoutExerciseRepository.save(WorkoutExercise(WorkoutExerciseId(workoutSessionId, workoutTypeId), numberOfReps))

    fun getWorkoutSessions(): List<WorkoutSession> = workoutSessionRepository.findAll()

    fun createWorkoutSession(workoutDate: LocalDate): WorkoutSession = workoutSessionRepository.save(WorkoutSession(0, workoutDate))
}
