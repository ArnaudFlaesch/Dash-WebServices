package com.dash.infra.repository

import com.dash.domain.model.workoutwidget.WorkoutSession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkoutSessionRepository : JpaRepository<WorkoutSession, Int>
