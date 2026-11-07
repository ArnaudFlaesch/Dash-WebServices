package com.dash.infra.entity

import com.dash.domain.model.WidgetDomain
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "widget")
class WidgetEntity(
    @Id
    @SequenceGenerator(
        name = "widget-seq-gen",
        sequenceName = "widget_id_seq",
        initialValue = 1,
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "widget-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,
    val type: Int,
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    var data: Any? = null,
    var widgetOrder: Int,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tabId")
    val tab: TabEntity
) {
    fun toDomain(): WidgetDomain =
        WidgetDomain(
            id = this.id,
            type = this.type,
            data = this.data,
            widgetOrder = this.widgetOrder,
            tabId = this.tab.id
        )
}
