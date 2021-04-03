package com.dash.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator

@Entity
data class Tab(
    @Id
    @SequenceGenerator(name = "tab-seq-gen", sequenceName = "tab_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tab-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,

    var label: String? = null,

    var tabOrder: Int? = 0
)
