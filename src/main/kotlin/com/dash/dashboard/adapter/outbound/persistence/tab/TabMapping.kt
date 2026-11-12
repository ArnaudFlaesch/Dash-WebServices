package com.dash.dashboard.adapter.outbound.persistence.tab

import com.dash.dashboard.domain.model.tab.Tab

fun TabEntity.toDomain(): Tab =
    Tab(
        this.id,
        this.label,
        this.tabOrder,
        this.user.id
    )
