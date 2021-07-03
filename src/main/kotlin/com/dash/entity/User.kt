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
    val id: Long,
    @NotBlank
    @Size(max = 20) var username: String?,
    @NotBlank
    @Size(max = 50)
    @Email var email: String?,
    @NotBlank
    @Size(max = 120) var password: String?,
    @ManyToOne
    @JoinColumn(name = "roleId")
    val role: Role
)
