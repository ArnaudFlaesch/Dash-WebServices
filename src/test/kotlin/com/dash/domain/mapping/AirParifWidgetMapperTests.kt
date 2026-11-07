package com.dash.domain.mapping

import com.dash.domain.model.airParif.AirParifColor
import com.dash.domain.model.airParif.AirParifPrevisionEnum
import com.dash.domain.model.airParif.Prevision
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AirParifWidgetMapperTests {
    @Test
    fun shouldMapColorsAndDefaultMissingOnesToEmpty() {
        val colors = colorsResponseToDomain(mapOf("Bon" to "#50F0E6", "Mauvais" to "#FF5050"))

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
            previsionsResponseToDomain("75112", mapOf("75112" to listOf(mapOf("no2" to "Bon"))))

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
        val previsions = previsionsResponseToDomain("75112", emptyMap())

        val missing = AirParifPrevisionEnum.MISSING
        assertEquals(listOf(Prevision("", missing, missing, missing, missing, missing, missing)), previsions)
    }
}
