package com.dash.dashboard.adapter.outbound.rest.airparif

import com.dash.dashboard.domain.model.airparif.AirParifColor
import com.dash.dashboard.domain.model.airparif.AirParifPrevisionEnum
import com.dash.dashboard.domain.model.airparif.Prevision

/** Maps the AirParif colors to every known prevision level, a level missing from the response getting no color. */
fun AirParifColorsResponse.toDomain(): List<AirParifColor> =
    AirParifPrevisionEnum.entries
        .filter { it != AirParifPrevisionEnum.MISSING }
        .map { AirParifColor(it, this[it.prevision].orEmpty()) }

/** Maps the previsions of a commune, an unknown commune giving a single prevision with every level missing. */
fun AirParifPrevisionsResponse.toDomain(communeInseeCode: String): List<Prevision> =
    getOrDefault(communeInseeCode, listOf(emptyMap())).map { prevision ->
        Prevision(
            date = prevision["date"].orEmpty(),
            no2 = prevision.levelOf("no2"),
            o3 = prevision.levelOf("o3"),
            pm10 = prevision.levelOf("pm10"),
            pm25 = prevision.levelOf("pm25"),
            so2 = prevision.levelOf("so2"),
            indice = prevision.levelOf("indice")
        )
    }

private fun Map<String, String>.levelOf(pollutant: String): AirParifPrevisionEnum =
    AirParifPrevisionEnum.getEnumFromValue(this[pollutant].orEmpty())
