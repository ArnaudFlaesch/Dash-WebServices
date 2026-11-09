package com.dash.dashboard.adapter.outbound.persistence.widget

import com.dash.dashboard.domain.model.widget.MiniWidget
import com.dash.shared.adapter.outbound.persistence.user.UserEntity
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
    var id: Int,
    var type: Int,
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    var data: Any? = null,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    var user: UserEntity
)
