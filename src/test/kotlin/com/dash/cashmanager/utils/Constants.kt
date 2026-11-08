package com.dash.cashmanager.utils

object Constants {
    const val LABEL_ENDPOINT = "/labels"

    fun labelEndpoint(labelId: Int?): String = "$LABEL_ENDPOINT/$labelId"
}
