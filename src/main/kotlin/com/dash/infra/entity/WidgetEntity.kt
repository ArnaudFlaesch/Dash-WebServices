package com.dash.infra.entity

import com.dash.domain.model.WidgetDomain
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import com.vladmihalcea.hibernate.type.json.JsonStringType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "widget")
@TypeDefs(
    TypeDef(name = "json", typeClass = JsonStringType::class),
    TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
)
data class WidgetEntity(
    @Id
    @SequenceGenerator(name = "widget-seq-gen", sequenceName = "widget_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "widget-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,

    val type: Int,

    @Type(type = "jsonb")
    @Column(columnDefinition = "json")
    val data: Any? = null,

    val widgetOrder: Int,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tabId")
    val tab: TabEntity
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1
    }

    fun toDomain() = WidgetDomain(
        id = this.id,
        type = this.type,
        data = this.data,
        widgetOrder = this.widgetOrder,
        tabId = this.tab.id
    )
}
