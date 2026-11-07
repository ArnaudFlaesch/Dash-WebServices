package com.dash.dashboard.adapter.outbound.persistence.workout

import com.dash.shared.adapter.outbound.persistence.user.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WorkoutTypeRepositoryTests {
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
