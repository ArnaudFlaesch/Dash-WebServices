package com.dash.domain.model.airParif


data class Prevision(
    val date: String,
    val no2: AirParifPrevisionEnum,
    val o3: AirParifPrevisionEnum,
    val pm10: AirParifPrevisionEnum,
    val pm25: AirParifPrevisionEnum,
    val so2: AirParifPrevisionEnum,
    val indice: AirParifPrevisionEnum
)
