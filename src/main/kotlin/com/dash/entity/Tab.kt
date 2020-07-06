package com.dash.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Tab(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,

        val label: String?,

        val tabOrder: Int?
)
