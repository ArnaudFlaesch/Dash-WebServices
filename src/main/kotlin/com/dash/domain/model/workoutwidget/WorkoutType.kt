package com.dash.domain.model.workoutwidget

import com.dash.infra.entity.User
import javax.persistence.*

@Entity
data class WorkoutType(
    @Id
    @SequenceGenerator(name = "workout-type-seq-gen", sequenceName = "workout_type_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workout-type-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,

    @Column(name = "name")
    val name: String,

    @ManyToOne(optional = true)
    @JoinColumn(name = "userId")
    val user: User
)
