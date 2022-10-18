package com.dash.infra.entity

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "roles")
data class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @NotBlank
    @Column(length = 20)
    val name: String
)
