package com.dash.domain.service

import com.dash.domain.mapping.WorkoutExerciseMapper
import com.dash.domain.mapping.WorkoutSessionMapper
import com.dash.domain.mapping.WorkoutTypeMapper
import com.dash.domain.model.workoutwidget.WorkoutExerciseDomain
import com.dash.domain.model.workoutwidget.WorkoutSessionDomain
import com.dash.domain.model.workoutwidget.WorkoutTypeDomain
import com.dash.infra.entity.workoutwidget.WorkoutExerciseEntity
import com.dash.infra.entity.workoutwidget.WorkoutExerciseEntityId
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
    private lateinit var workoutSessionMapper: WorkoutSessionMapper

    @Autowired
    private lateinit var workoutExerciseMapper: WorkoutExerciseMapper

    @Autowired
    private lateinit var workoutTypeMapper: WorkoutTypeMapper

    fun getWorkoutTypes(userId: Int): List<WorkoutTypeDomain> = workoutTypeRepository.findByUserId(userId).map(workoutTypeMapper::mapEntityToDomain)

    fun getWorkoutSessions(userId: Int): List<WorkoutSessionDomain> =
        workoutSessionRepository.findByUserId(userId).map(workoutSessionMapper::mapEntityToDomain)

    fun addWorkoutType(workoutType: String, userId: Int): WorkoutTypeDomain {
        val workoutToInsert = WorkoutTypeDomain(0, workoutType, userId)
        return workoutTypeMapper.mapEntityToDomain(workoutTypeRepository.save(workoutTypeMapper.mapDomainToEntity(workoutToInsert)))
    }

    fun getWorkoutsExercisesByWorkoutSessionId(workoutSessionId: Int): List<WorkoutExerciseDomain> =
        workoutExerciseRepository.findAllByWorkoutSessionId(workoutSessionId).map(workoutExerciseMapper::mapEntityToDomain)

    fun updateWorkoutExercise(workoutSessionId: Int, workoutTypeId: Int, numberOfReps: Int): WorkoutExerciseDomain {
        val workoutExerciseToInsert = WorkoutExerciseEntity(WorkoutExerciseEntityId(workoutSessionId, workoutTypeId), numberOfReps)
        return workoutExerciseMapper.mapEntityToDomain(workoutExerciseRepository.save(workoutExerciseToInsert))
    }

    fun createWorkoutSession(workoutDate: LocalDate, userId: Int): WorkoutSessionDomain {
        val newWorkoutSession = WorkoutSessionDomain(0, workoutDate, userId)
        val insertedWorkoutSession = workoutSessionRepository.save(workoutSessionMapper.mapDomainToEntity(newWorkoutSession))
        return workoutSessionMapper.mapEntityToDomain(insertedWorkoutSession)
    }
}
