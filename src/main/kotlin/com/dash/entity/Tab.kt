package com.dash.entity

import javax.persistence.*

@Entity
data class Tab(
        @Id
        @SequenceGenerator(name="tab-seq-gen", sequenceName="TAB_SEQ", initialValue=205, allocationSize=12)
		@GeneratedValue(strategy= GenerationType.IDENTITY, generator="tab-seq-gen")
		@Column(name="id",unique=true,nullable=false)
        var id: Int? = null,

        var label: String? = null,

        var tabOrder: Int? = 0
)
