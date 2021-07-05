package com.dash.entity

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["username"]),
        UniqueConstraint(columnNames = ["email"])
    ]
)
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    @NotBlank
    @Size(max = 20) val username: String,
    @NotBlank
    @Size(max = 50)
    @Email val email: String,
    @NotBlank
    @Size(max = 120) val password: String,
    @ManyToOne
    @JoinColumn(name = "roleId")
    val role: Role
)
