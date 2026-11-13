package com.dash.dashboard.domain.model.tab

data class Tab(
    val id: Int,
    val label: String,
    val tabOrder: Int,
    val userId: Int
) {
    companion object {
        /** A tab not saved yet, placed after the user's [numberOfTabs] existing tabs. */
        fun newAfter(
            numberOfTabs: Int,
            label: String,
            userId: Int
        ): Tab = Tab(id = 0, label = label, tabOrder = numberOfTabs + 1, userId = userId)
    }
}
