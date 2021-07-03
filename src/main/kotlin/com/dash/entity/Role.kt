package com.dash.entity

import com.dash.enums.RoleEnum
import javax.persistence.*

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    val name: RoleEnum
)
