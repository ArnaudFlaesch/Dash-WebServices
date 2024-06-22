package com.dash.infra.entity

import com.common.infra.entity.UserEntity
import com.dash.domain.model.TabDomain
import jakarta.persistence.*

@Entity
@Table(name = "tab")
data class TabEntity(
    @Id
    @SequenceGenerator(name = "tab-seq-gen", sequenceName = "tab_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tab-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,
    val label: String,
    val tabOrder: Int,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    val user: UserEntity
) {
    fun toDomain(): TabDomain =
        TabDomain(
            this.id,
            this.label,
            this.tabOrder,
            this.user.id
        )
}
