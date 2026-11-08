package com.dash.dashboard.adapter.outbound.persistence.workout

import jakarta.persistence.*
import java.io.Serializable

@Embeddable
data class WorkoutExerciseEntityId(
    @Column(name = "workout_session_id")
    val workoutSessionId: Int,
    @Column(name = "workout_type_id")
    val workoutTypeId: Int
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
