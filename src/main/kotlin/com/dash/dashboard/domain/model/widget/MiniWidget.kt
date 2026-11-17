package com.dash.dashboard.domain.model.widget

data class MiniWidget(
    val id: Int,
    val type: Int,
    val data: Any?,
    val userId: Int
) {
    companion object {
        /** A mini widget not saved yet, without data. */
        fun new(
            type: Int,
            userId: Int
        ): MiniWidget = MiniWidget(id = 0, type = type, data = null, userId = userId)
    }
}
