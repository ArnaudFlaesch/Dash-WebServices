package com.dash.infra.repository

import com.common.infra.repository.UserRepository
import com.common.utils.AbstractIT
import com.dash.infra.entity.workoutwidget.WorkoutSessionEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class WorkoutSessionRepositoryTests : AbstractIT() {

    @Autowired
    private lateinit var workoutSessionRepository: WorkoutSessionRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun testInsertWorkoutSessions() {
        val authenticatedUser = userRepository.getReferenceById(1)
        val workoutSessions = listOf(
            WorkoutSessionEntity(0, LocalDate.of(2023, 1, 1), authenticatedUser),
            WorkoutSessionEntity(0, LocalDate.of(2023, 1, 5), authenticatedUser)
        )

        workoutSessionRepository.saveAll(workoutSessions)

        val listWorkoutSessions = workoutSessionRepository.findByUserIdAndWorkoutDateBetweenOrderByWorkoutDateAsc(
            authenticatedUser.id,
            LocalDate.of(2023, 1, 1),
            LocalDate.now()
        )
        assertThat(listWorkoutSessions).hasSize(2)

        Assertions.assertNotNull(listWorkoutSessions[0].id)
        assertEquals(LocalDate.of(2023, 1, 1), listWorkoutSessions[0].workoutDate)
        assertEquals(authenticatedUser.id, listWorkoutSessions[0].user.id)
        assertEquals(LocalDate.of(2023, 1, 5), listWorkoutSessions[1].workoutDate)
        assertEquals(authenticatedUser.id, listWorkoutSessions[1].user.id)
    }
}
