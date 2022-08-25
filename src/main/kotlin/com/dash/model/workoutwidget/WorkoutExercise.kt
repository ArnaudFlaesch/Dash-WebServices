package com.dash.model.workoutwidget

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "workout_exercise")
class WorkoutExercise {
    @EmbeddedId
    var workoutExerciseId: WorkoutExerciseId
    var numberOfReps: Int = 0

    @ManyToOne
    @JoinColumn(name = "workout_session_id", insertable = false, updatable = false)
    lateinit var workoutSession: WorkoutType

    @ManyToOne
    @JoinColumn(name = "workout_type_id", insertable = false, updatable = false)
    lateinit var workoutType: WorkoutType

    constructor(workoutExerciseId: WorkoutExerciseId, numberOfReps: Int) {
        this.workoutExerciseId = workoutExerciseId
        this.numberOfReps = numberOfReps
    }

}

@Embeddable
class WorkoutExerciseId : Serializable {
    @Column(name = "workout_session_id")
    val workoutSessionId: Int

    @Column(name = "workout_type_id")
    val workoutTypeId: Int

    constructor(workoutSessionId: Int, workoutTypeId: Int) {
        this.workoutSessionId = workoutSessionId
        this.workoutTypeId = workoutTypeId
    }
}
