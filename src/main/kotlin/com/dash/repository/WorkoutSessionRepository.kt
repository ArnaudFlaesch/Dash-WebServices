package com.dash.repository

import com.dash.model.workoutwidget.WorkoutSession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkoutSessionRepository : JpaRepository<WorkoutSession, Int>
