package com.dash.shared.adapter.outbound.persistence.user

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["id"]),
        UniqueConstraint(columnNames = ["username"]),
        UniqueConstraint(columnNames = ["email"])
    ]
)
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    var id: Int,
    @NotBlank
    @Size(max = 20)
    var username: String,
    @NotBlank
    @Size(max = 50)
    @Email
    var email: String,
    @NotBlank
    @Size(max = 120)
    var password: String,
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId")
    var role: RoleEntity
)
