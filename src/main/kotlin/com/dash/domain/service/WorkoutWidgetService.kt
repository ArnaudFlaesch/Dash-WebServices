package com.dash.domain.service

import com.common.domain.service.UserService
import com.dash.domain.mapping.WorkoutSessionMapper
import com.dash.domain.mapping.WorkoutTypeMapper
import com.dash.domain.model.workoutwidget.WorkoutExerciseDomain
import com.dash.domain.model.workoutwidget.WorkoutSessionDomain
import com.dash.domain.model.workoutwidget.WorkoutStatsByMonthDomain
import com.dash.domain.model.workoutwidget.WorkoutTypeDomain
import com.dash.infra.entity.workoutwidget.*
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
    private val workoutTypeMapper: WorkoutTypeMapper,
    private val userService: UserService
) {

    fun getWorkoutTypes(): List<WorkoutTypeDomain> {
        val authenticatedUserId = userService.getCurrentAuthenticatedUserId()
        return workoutTypeRepository.findByUserId(authenticatedUserId).map(WorkoutTypeEntity::toDomain)
    }

    fun getWorkoutSessions(): List<WorkoutSessionDomain> {
        val authenticatedUserId = userService.getCurrentAuthenticatedUserId()
        return workoutSessionRepository.findByUserIdOrderByWorkoutDateAsc(authenticatedUserId)
            .map(WorkoutSessionEntity::toDomain)
    }

    fun getWorkoutStatsByMonth(dateMonth: LocalDate): List<WorkoutStatsByMonthDomain> {
        val authenticatedUserId = userService.getCurrentAuthenticatedUserId()
        return workoutExerciseRepository.getWorkoutStatsByMonth(dateMonth.monthValue, dateMonth.year, authenticatedUserId)
            .map(WorkoutStatsByMonthEntity::toDomain)
    }

    fun addWorkoutType(workoutType: String): WorkoutTypeDomain {
        val authenticatedUserId = userService.getCurrentAuthenticatedUserId()
        val workoutToInsert = WorkoutTypeDomain(0, workoutType, authenticatedUserId)
        return workoutTypeRepository.save(workoutTypeMapper.mapDomainToEntity(workoutToInsert)).toDomain()
    }

    fun getWorkoutsExercisesByWorkoutSessionId(workoutSessionId: Int): List<WorkoutExerciseDomain> =
        workoutExerciseRepository.findAllByWorkoutSessionId(workoutSessionId).map(WorkoutExerciseEntity::toDomain)

    fun updateWorkoutExercise(workoutSessionId: Int, workoutTypeId: Int, numberOfReps: Int): WorkoutExerciseDomain {
        val workoutExerciseToInsert = WorkoutExerciseEntity(WorkoutExerciseEntityId(workoutSessionId, workoutTypeId), numberOfReps)
        return workoutExerciseRepository.save(workoutExerciseToInsert).toDomain()
    }

    fun createWorkoutSession(workoutDate: LocalDate): WorkoutSessionDomain {
        val authenticatedUserId = userService.getCurrentAuthenticatedUserId()
        val newWorkoutSession = WorkoutSessionDomain(0, workoutDate, authenticatedUserId)
        return workoutSessionRepository.save(workoutSessionMapper.mapDomainToEntity(newWorkoutSession)).toDomain()
    }
}
