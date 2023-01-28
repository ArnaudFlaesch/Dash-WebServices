package com.dash.infra.repository

import com.dash.infra.entity.workoutwidget.WorkoutExerciseEntity
import com.dash.infra.entity.workoutwidget.WorkoutExerciseEntityId
import com.dash.infra.entity.workoutwidget.WorkoutStatsByIntervalEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface WorkoutExerciseRepository : JpaRepository<WorkoutExerciseEntity, WorkoutExerciseEntityId> {

    @Query("SELECT * FROM workout_exercise WHERE workout_session_id = :workoutSessionId", nativeQuery = true)
    fun findAllByWorkoutSessionId(workoutSessionId: Int): List<WorkoutExerciseEntity>

    @Query(name = "getWorkoutStatsByInterval", nativeQuery = true)
    fun getWorkoutStatsByInterval(dateIntervalStart: LocalDate, dateIntervalEnd: LocalDate, userId: Int): List<WorkoutStatsByIntervalEntity>
}
