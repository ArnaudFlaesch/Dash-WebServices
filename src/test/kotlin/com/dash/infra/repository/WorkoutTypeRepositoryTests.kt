package com.dash.infra.repository

import com.common.infra.repository.UserRepository
import com.common.utils.AbstractIT
import com.dash.infra.entity.workoutwidget.WorkoutTypeEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WorkoutTypeRepositoryTests : AbstractIT() {
    @Autowired
    private lateinit var workoutTypeRepository: WorkoutTypeRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun testInsertWorkoutTypes() {
        val authenticatedUser = userRepository.getReferenceById(1)
        val workoutTypes =
            listOf(
                WorkoutTypeEntity(0, "Running", authenticatedUser),
                WorkoutTypeEntity(0, "Swimming", authenticatedUser)
            )

        workoutTypeRepository.saveAll(workoutTypes)

        val listWorkoutTypes =
            workoutTypeRepository.findByUserIdOrderByNameAsc(
                authenticatedUser.id
            )
        assertThat(listWorkoutTypes).hasSize(2)

        assertNotNull(listWorkoutTypes[0].id)
        assertEquals("Running", listWorkoutTypes[0].name)
        assertEquals(authenticatedUser.id, listWorkoutTypes[0].user.id)
        assertEquals("Swimming", listWorkoutTypes[1].name)
        assertEquals(authenticatedUser.id, listWorkoutTypes[1].user.id)
    }
}
