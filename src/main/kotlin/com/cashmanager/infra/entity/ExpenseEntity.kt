package com.cashmanager.infra.entity

import com.cashmanager.domain.model.ExpenseDomain
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*

@SqlResultSetMapping(
    name = "totalExpensesByMonth",
    classes = [
        ConstructorResult(
            targetClass = TotalExpenseByMonthEntity::class,
            columns = [ColumnResult(name = "total"), ColumnResult(name = "date")]
        )
    ]
)
// @FIXME revert to using multiple chained @NamedNativeQuery annotations instead of @NamedNativeQueries
@NamedNativeQueries(
    value = [
        NamedNativeQuery(
            query = "SELECT SUM(amount) AS total, CAST(date_trunc('month', E.expense_date) AS DATE) as date " +
                "FROM Expense E " +
                "WHERE E.label_id IN (SELECT id FROM label L WHERE L.user_id = :userId)" +
                "GROUP BY CAST(date_trunc('month', E.expense_date) AS DATE)",
            name = "getExpensesByMonth",
            resultClass = TotalExpenseByMonthEntity::class,
            resultSetMapping = "totalExpensesByMonth"
        ),
        NamedNativeQuery(
            query = "SELECT SUM(amount) AS total, CAST(date_trunc('month', E.expense_date) AS DATE) as date " +
                "FROM Expense E " +
                "WHERE label_id = :labelId " +
                "AND E.label_id IN (SELECT id FROM label L WHERE L.user_id = :userId)" +
                "GROUP BY CAST(date_trunc('month', E.expense_date) AS DATE)",
            name = "getExpensesByMonthByLabelId",
            resultClass = TotalExpenseByMonthEntity::class,
            resultSetMapping = "totalExpensesByMonth"
        )
    ]
)
@Entity
@Table(name = "expense")
data class ExpenseEntity(
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
    val label: LabelEntity
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1
    }

    fun toDomain() = ExpenseDomain(id = this.id, amount = this.amount, expenseDate = this.expenseDate, labelId = label.id)
}
