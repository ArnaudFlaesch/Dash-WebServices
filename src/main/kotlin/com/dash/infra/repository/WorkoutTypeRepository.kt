package com.dash.infra.repository

import com.dash.domain.model.workoutwidget.WorkoutType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkoutTypeRepository : JpaRepository<WorkoutType, Int>