package com.dash.domain.model.airParif

enum class AirParifPrevisionEnum(
    val prevision: String
) {
    BON("Bon"),
    MOYEN("Moyen"),
    DEGRADE("Dégradé"),
    MAUVAIS("Mauvais"),
    TRES_MAUVAIS("Très Mauvais"),
    EXTREMEMENT_MAUVAIS("Extrêmement Mauvais"),
    MISSING("Missing");

    companion object {
        fun getEnumFromValue(value: String): AirParifPrevisionEnum = entries.find { it.prevision == value } ?: MISSING
    }
}
