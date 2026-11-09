package com.dash.shared.adapter.outbound.persistence.user

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "roles")
class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,
    @NotBlank
    @Column(length = 20)
    var name: String
)
