package com.dash.model.airParif

enum class AirParifPrevisionEnum(val prevision: String) {
    MAUVAIS("Mauvais"),
    DEGRADE("Dégradé"),
    MOYEN("Moyen"),
    BON("Bon")
}

data class Prevision(
    val date: String,
    val no2: AirParifPrevisionEnum,
    val o3: AirParifPrevisionEnum,
    val pm10: AirParifPrevisionEnum,
    val pm25: AirParifPrevisionEnum,
    val so2: AirParifPrevisionEnum,
    val indice: AirParifPrevisionEnum
)
