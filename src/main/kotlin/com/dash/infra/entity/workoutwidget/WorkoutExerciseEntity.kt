package com.dash.infra.entity.workoutwidget

import com.dash.domain.model.workoutwidget.WorkoutExerciseDomain
import jakarta.persistence.*
import java.io.Serializable

@NamedNativeQueries(
    NamedNativeQuery(
        query = """SELECT sum(E.number_of_reps) AS totalNumberOfReps, T.name AS workoutTypeName
            FROM Workout_Exercise E
            RIGHT JOIN Workout_Session S ON E.workout_session_id = S.id
            RIGHT JOIN Workout_Type T ON E.workout_type_id = T.id
            WHERE S.workout_date BETWEEN :dateIntervalStart AND :dateIntervalEnd
            AND S.user_id = :userId
            GROUP BY T.name""",
        name = "getWorkoutStatsByInterval",
        resultClass = WorkoutStatsByIntervalEntity::class,
        resultSetMapping = "workoutStatsByInterval"
    ),
    NamedNativeQuery(
        query = """SELECT E.workout_type_id AS workoutTypeId, SUM(E.number_of_reps) as totalNumberOfReps,
            T.name as workoutTypeName, EXTRACT(MONTH FROM S.workout_date) AS monthPeriod,
            EXTRACT(YEAR FROM S.workout_date) AS yearPeriod
            FROM Workout_Exercise E
            RIGHT JOIN Workout_Session S ON E.workout_session_id = S.id
            RIGHT JOIN Workout_Type T ON E.workout_type_id = T.id
            WHERE S.workout_date BETWEEN :dateIntervalStart AND :dateIntervalEnd
            AND S.user_id = :userId
            GROUP BY monthPeriod, workoutTypeId, yearPeriod, workoutTypeName
            ORDER BY monthPeriod ASC""",
        name = "getWorkoutStatsByMonth",
        resultClass = WorkoutStatsByMonthEntity::class,
        resultSetMapping = "workoutStatsByMonth"
    )
)
@SqlResultSetMappings(
    SqlResultSetMapping(
        name = "workoutStatsByInterval",
        classes = [
            ConstructorResult(
                targetClass = WorkoutStatsByIntervalEntity::class,
                columns = [
                    ColumnResult(name = "totalNumberOfReps"),
                    ColumnResult(name = "workoutTypeName")
                ]
            )
        ]
    ),
    SqlResultSetMapping(
        name = "workoutStatsByMonth",
        classes = [
            ConstructorResult(
                targetClass = WorkoutStatsByMonthEntity::class,
                columns = [
                    ColumnResult(name = "workoutTypeId"),
                    ColumnResult(name = "totalNumberOfReps"),
                    ColumnResult(name = "workoutTypeName"),
                    ColumnResult(name = "monthPeriod"),
                    ColumnResult(
                        name = "yearPeriod"
                    )
                ]
            )
        ]
    )
)
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
