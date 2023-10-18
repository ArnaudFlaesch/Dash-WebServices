package com.dash.infra.entity

import com.common.infra.entity.UserEntity
import com.dash.domain.model.MiniWidgetDomain
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import java.io.Serializable

@Entity
@Table(name = "mini_widget")
data class MiniWidgetEntity(
    @Id
    @SequenceGenerator(name = "mini-widget-seq-gen", sequenceName = "mini_widget_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mini-widget-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,
    val type: Int,
    @Type(JsonBinaryType::class)
    @Column(columnDefinition = "json")
    val data: Any? = null,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    val user: UserEntity
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1
    }

    fun toDomain() =
        MiniWidgetDomain(
            id = this.id,
            type = this.type,
            data = this.data,
            userId = this.user.id
        )
}
