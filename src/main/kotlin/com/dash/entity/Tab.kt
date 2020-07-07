package com.dash.entity

import javax.persistence.*

@Entity
data class Tab(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,

        val label: String?,

        val tabOrder: Int?
)
