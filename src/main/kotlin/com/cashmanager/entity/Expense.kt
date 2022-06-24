package com.cashmanager.entity

import com.cashmanager.model.TotalExpenseByMonth
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*

@SqlResultSetMapping(
    name = "totalExpensesByMonth",
    classes = [
        ConstructorResult(
            targetClass = TotalExpenseByMonth::class,
            columns = [ColumnResult(name = "total"), ColumnResult(name = "date")]
        )
    ]
)
@NamedNativeQuery(
    query = "SELECT SUM(amount) AS total, CAST(date_trunc('month', E.expense_date) AS DATE) as date " +
        "FROM Expense E " +
        "GROUP BY CAST(date_trunc('month', E.expense_date) AS DATE)",
    name = "getExpensesByMonth",
    resultClass = TotalExpenseByMonth::class,
    resultSetMapping = "totalExpensesByMonth"
)
@NamedNativeQuery(
    query = "SELECT SUM(amount) AS total, CAST(date_trunc('month', E.expense_date) AS DATE) as date " +
        "FROM Expense E " +
        "WHERE label_id = :labelId " +
        "GROUP BY CAST(date_trunc('month', E.expense_date) AS DATE)",
    name = "getExpensesByMonthByLabelId",
    resultClass = TotalExpenseByMonth::class,
    resultSetMapping = "totalExpensesByMonth"
)
@Entity
data class Expense(
    @Id
    @SequenceGenerator(name = "expense-seq-gen", sequenceName = "expense_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,

    @Column
    val amount: Float,

    @Column(name = "expense_date")
    val expenseDate: LocalDate,

    @ManyToOne(optional = true)
    @JoinColumn(name = "labelId")
    val label: Label
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1
    }
}
