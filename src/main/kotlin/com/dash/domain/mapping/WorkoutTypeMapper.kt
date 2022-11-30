package com.dash.domain.mapping

import com.common.domain.service.UserService
import com.dash.domain.model.workoutwidget.WorkoutTypeDomain
import com.dash.infra.entity.workoutwidget.WorkoutTypeEntity
import org.springframework.stereotype.Component

@Component
class WorkoutTypeMapper(private val userService: UserService) {

    fun mapDomainToEntity(workoutTypeDomain: WorkoutTypeDomain): WorkoutTypeEntity =
        WorkoutTypeEntity(
            id = workoutTypeDomain.id,
            name = workoutTypeDomain.name,
            user = userService.getUserById(workoutTypeDomain.userId)
        )
}
