package com.dash.model.workoutwidget

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "workout_exercise")
data class WorkoutExercise(
    @EmbeddedId
    var workoutExerciseId: WorkoutExerciseId,
    var numberOfReps: Int = 0,

    @ManyToOne
    @JoinColumn(name = "workout_session_id", insertable = false, updatable = false)
    var workoutSession: WorkoutSession? = null,

    @ManyToOne
    @JoinColumn(name = "workout_type_id", insertable = false, updatable = false)
    var workoutType: WorkoutType? = null
)

@Embeddable
data class WorkoutExerciseId(

    @Column(name = "workout_session_id")
    val workoutSessionId: Int,

    @Column(name = "workout_type_id")
    val workoutTypeId: Int
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
