package com.cashmanager.entity

import javax.persistence.*

@Entity
data class Label(
    @Id
    @SequenceGenerator(name = "label-seq-gen", sequenceName = "label_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "label-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,

    @Column(name = "label")
    val label: String = ""
)
