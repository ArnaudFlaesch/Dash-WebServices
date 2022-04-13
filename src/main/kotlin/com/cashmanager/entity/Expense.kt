package com.cashmanager.entity

import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
data class Expense(
    @Id
    @SequenceGenerator(name = "expense-seq-gen", sequenceName = "expense_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,

    val amount: Int,

    @Temporal(TemporalType.DATE)
    val expenseDate: Date,

    @ManyToOne(optional = true)
    @JoinColumn(name = "labelId")
    val label: Label
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1
    }
}
