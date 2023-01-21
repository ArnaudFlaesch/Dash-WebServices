package com.dash.domain.service

import com.dash.domain.mapping.WorkoutSessionMapper
import com.dash.domain.mapping.WorkoutTypeMapper
import com.dash.domain.model.workoutwidget.WorkoutExerciseDomain
import com.dash.domain.model.workoutwidget.WorkoutSessionDomain
import com.dash.domain.model.workoutwidget.WorkoutTypeDomain
import com.dash.infra.entity.workoutwidget.WorkoutExerciseEntity
import com.dash.infra.entity.workoutwidget.WorkoutExerciseEntityId
import com.dash.infra.entity.workoutwidget.WorkoutSessionEntity
import com.dash.infra.entity.workoutwidget.WorkoutTypeEntity
import com.dash.infra.repository.WorkoutExerciseRepository
import com.dash.infra.repository.WorkoutSessionRepository
import com.dash.infra.repository.WorkoutTypeRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class WorkoutWidgetService(
    private val workoutTypeRepository: WorkoutTypeRepository,
    private val workoutExerciseRepository: WorkoutExerciseRepository,
    private val workoutSessionRepository: WorkoutSessionRepository,
    private val workoutSessionMapper: WorkoutSessionMapper,
    private val workoutTypeMapper: WorkoutTypeMapper
) {

    fun getWorkoutTypes(userId: Int): List<WorkoutTypeDomain> =
        workoutTypeRepository.findByUserId(userId).map(WorkoutTypeEntity::toDomain)

    fun getWorkoutSessions(userId: Int): List<WorkoutSessionDomain> =
        workoutSessionRepository.findByUserIdOrderByWorkoutDateAsc(userId).map(WorkoutSessionEntity::toDomain)

    fun addWorkoutType(workoutType: String, userId: Int): WorkoutTypeDomain {
        val workoutToInsert = WorkoutTypeDomain(0, workoutType, userId)
        return workoutTypeRepository.save(workoutTypeMapper.mapDomainToEntity(workoutToInsert)).toDomain()
    }

    fun getWorkoutsExercisesByWorkoutSessionId(workoutSessionId: Int): List<WorkoutExerciseDomain> =
        workoutExerciseRepository.findAllByWorkoutSessionId(workoutSessionId).map(WorkoutExerciseEntity::toDomain)

    fun updateWorkoutExercise(workoutSessionId: Int, workoutTypeId: Int, numberOfReps: Int): WorkoutExerciseDomain {
        val workoutExerciseToInsert = WorkoutExerciseEntity(WorkoutExerciseEntityId(workoutSessionId, workoutTypeId), numberOfReps)
        return workoutExerciseRepository.save(workoutExerciseToInsert).toDomain()
    }

    fun createWorkoutSession(workoutDate: LocalDate, userId: Int): WorkoutSessionDomain {
        val newWorkoutSession = WorkoutSessionDomain(0, workoutDate, userId)
        val insertedWorkoutSession = workoutSessionRepository.save(workoutSessionMapper.mapDomainToEntity(newWorkoutSession))
        return insertedWorkoutSession.toDomain()
    }
}
