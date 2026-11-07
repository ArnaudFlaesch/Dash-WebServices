package com.dash.dashboard.adapter.outbound.persistence.tab

import com.dash.dashboard.domain.model.tab.Tab
import com.dash.shared.adapter.outbound.persistence.user.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "tab")
class TabEntity(
    @Id
    @SequenceGenerator(
        name = "tab-seq-gen",
        sequenceName = "tab_id_seq",
        initialValue = 1,
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tab-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    val id: Int,
    var label: String,
    var tabOrder: Int,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    val user: UserEntity
) {
    fun toDomain(): Tab =
        Tab(
            this.id,
            this.label,
            this.tabOrder,
            this.user.id
        )
}
