package com.dash.infra.entity.workoutwidget

import com.dash.domain.model.workoutwidget.WorkoutExerciseDomain
import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "workout_exercise")
data class WorkoutExerciseEntity(
    @EmbeddedId
    var workoutExerciseId: WorkoutExerciseEntityId,
    var numberOfReps: Int = 0,

    @ManyToOne
    @JoinColumn(name = "workout_session_id", insertable = false, updatable = false)
    var workoutSession: WorkoutSessionEntity? = null,

    @ManyToOne
    @JoinColumn(name = "workout_type_id", insertable = false, updatable = false)
    var workoutType: WorkoutTypeEntity? = null
) {
    fun toDomain(): WorkoutExerciseDomain =
        WorkoutExerciseDomain(
            workoutSessionId = this.workoutExerciseId.workoutSessionId,
            workoutTypeId = this.workoutExerciseId.workoutTypeId,
            numberOfReps = this.numberOfReps
        )
}

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
