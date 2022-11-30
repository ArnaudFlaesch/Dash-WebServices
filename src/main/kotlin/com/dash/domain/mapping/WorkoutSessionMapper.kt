package com.dash.domain.mapping

import com.common.domain.service.UserService
import com.dash.domain.model.workoutwidget.WorkoutSessionDomain
import com.dash.infra.entity.workoutwidget.WorkoutSessionEntity
import org.springframework.stereotype.Component

@Component
class WorkoutSessionMapper(private val userService: UserService) {

    fun mapDomainToEntity(workoutSessionDomain: WorkoutSessionDomain): WorkoutSessionEntity =
        WorkoutSessionEntity(
            id = workoutSessionDomain.id,
            workoutDate = workoutSessionDomain.workoutDate,
            user = userService.getUserById(workoutSessionDomain.userId)
        )
}
