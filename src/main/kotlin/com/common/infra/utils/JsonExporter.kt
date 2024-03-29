package com.common.infra.utils

import com.google.gson.Gson

object JsonExporter {
    fun export(entities: Any): String {
        val gson = Gson()
        return gson.toJson(entities)
    }
}
