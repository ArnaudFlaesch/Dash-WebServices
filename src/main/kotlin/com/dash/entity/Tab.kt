package com.dash.entity

import javax.persistence.*

@Entity
@Table(name = "tab")
data class Tab(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id : Number,
        val label: String,

        @Column(name = "tabOrder")
        val tabOrder: Number
)