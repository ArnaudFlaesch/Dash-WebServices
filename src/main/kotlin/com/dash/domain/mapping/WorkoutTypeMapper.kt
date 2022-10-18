package com.dash.domain.mapping

import com.common.domain.service.UserService
import com.dash.domain.model.workoutwidget.WorkoutTypeDomain
import com.dash.infra.entity.workoutwidget.WorkoutTypeEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class WorkoutTypeMapper {

    @Autowired
    private lateinit var userService: UserService

    fun mapEntityToDomain(workoutTypeEntity: WorkoutTypeEntity): WorkoutTypeDomain =
        WorkoutTypeDomain(
            id = workoutTypeEntity.id,
            name = workoutTypeEntity.name,
            userId = workoutTypeEntity.user.id
        )

    fun mapDomainToEntity(workoutTypeDomain: WorkoutTypeDomain): WorkoutTypeEntity =
        WorkoutTypeEntity(
            id = workoutTypeDomain.id,
            name = workoutTypeDomain.name,
            user = userService.getUserById(workoutTypeDomain.userId)
        )
}
