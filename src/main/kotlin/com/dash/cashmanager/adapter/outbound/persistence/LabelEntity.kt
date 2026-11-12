package com.dash.cashmanager.adapter.outbound.persistence

import com.dash.shared.adapter.outbound.persistence.user.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "label")
class LabelEntity(
    @Id
    @SequenceGenerator(
        name = "label-seq-gen",
        sequenceName = "label_id_seq",
        initialValue = 1,
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "label-seq-gen")
    @Column(name = "id", unique = true, nullable = false)
    var id: Int,
    @Column(name = "label", unique = true)
    var label: String,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    var user: UserEntity
)
