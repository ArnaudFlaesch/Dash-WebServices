package com.dash.entity

import javax.persistence.*

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(length = 20)
    val name: String
)
