package com.dash.repository

import com.dash.model.workoutwidget.WorkoutExercise
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface WorkoutExerciseRepository : JpaRepository<WorkoutExercise, Int> {

    @Query("SELECT * FROM workout_exercise WHERE workout_session_id = :workoutSessionId", nativeQuery = true)
    fun findAllByWorkoutSessionId(workoutSessionId: Int): List<WorkoutExercise>
}
