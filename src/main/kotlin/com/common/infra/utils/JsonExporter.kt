package com.common.infra.utils

import com.google.gson.Gson

object JsonExporter {
    fun export(entities: Any): String = Gson().toJson(entities)
}
