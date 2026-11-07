package com.dash.infra.entity

import com.common.infra.entity.UserEntity
import com.dash.domain.model.MiniWidgetDomain
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "mini_widget")
class MiniWidgetEntity(
    @Id
    @SequenceGenerator(
        name = "mini-widget-seq-gen",
        sequenceName = "mini_widget_id_seq",
        initialValue = 1,
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mini-widget-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,
    val type: Int,
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    var data: Any? = null,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    val user: UserEntity
) {
    fun toDomain(): MiniWidgetDomain =
        MiniWidgetDomain(
            id = this.id,
            type = this.type,
            data = this.data,
            userId = this.user.id
        )
}
