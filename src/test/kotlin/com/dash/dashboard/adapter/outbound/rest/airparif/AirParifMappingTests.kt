package com.dash.dashboard.adapter.outbound.rest.airparif

import com.dash.dashboard.domain.model.airparif.AirParifColor
import com.dash.dashboard.domain.model.airparif.AirParifPrevisionEnum
import com.dash.dashboard.domain.model.airparif.Prevision
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AirParifMappingTests {
    @Test
    fun shouldMapColorsAndDefaultMissingOnesToEmpty() {
        val colors = mapOf("Bon" to "#50F0E6", "Mauvais" to "#FF5050").toDomain()

        assertEquals(
            listOf(
                AirParifColor(AirParifPrevisionEnum.BON, "#50F0E6"),
                AirParifColor(AirParifPrevisionEnum.MOYEN, ""),
                AirParifColor(AirParifPrevisionEnum.DEGRADE, ""),
                AirParifColor(AirParifPrevisionEnum.MAUVAIS, "#FF5050"),
                AirParifColor(AirParifPrevisionEnum.TRES_MAUVAIS, ""),
                AirParifColor(AirParifPrevisionEnum.EXTREMEMENT_MAUVAIS, "")
            ),
            colors
        )
    }

    @Test
    fun shouldMapPrevisionWithMissingFields() {
        val previsions =
            mapOf("75112" to listOf(mapOf("no2" to "Bon"))).toDomain("75112")

        assertEquals(
            listOf(
                Prevision(
                    "",
                    AirParifPrevisionEnum.BON,
                    AirParifPrevisionEnum.MISSING,
                    AirParifPrevisionEnum.MISSING,
                    AirParifPrevisionEnum.MISSING,
                    AirParifPrevisionEnum.MISSING,
                    AirParifPrevisionEnum.MISSING
                )
            ),
            previsions
        )
    }

    @Test
    fun shouldReturnEmptyPrevisionWhenCommuneIsMissing() {
        val previsions = emptyMap<String, List<Map<String, String>>>().toDomain("75112")

        val missing = AirParifPrevisionEnum.MISSING
        assertEquals(listOf(Prevision("", missing, missing, missing, missing, missing, missing)), previsions)
    }
}
