package com.dash.domain.mapping

import com.dash.domain.model.airParif.AirParifColor
import com.dash.domain.model.airParif.AirParifPrevisionEnum
import com.dash.domain.model.airParif.Prevision

/** Maps the AirParif colors to every known prevision level, a level missing from the response getting no color. */
fun colorsResponseToDomain(colorsResponse: Map<String, String>): List<AirParifColor> =
    AirParifPrevisionEnum.entries
        .filter { it != AirParifPrevisionEnum.MISSING }
        .map { AirParifColor(it, colorsResponse[it.prevision].orEmpty()) }

/** Maps the previsions of a commune, an unknown commune giving a single prevision with every level missing. */
fun previsionsResponseToDomain(
    communeInseeCode: String,
    airParifPrevisionResponse: Map<String, List<Map<String, String>>>
): List<Prevision> =
    airParifPrevisionResponse
        .getOrDefault(communeInseeCode, listOf(emptyMap()))
        .map(::previsionResponseToDomain)

private fun previsionResponseToDomain(previsionResponse: Map<String, String>): Prevision =
    Prevision(
        date = previsionResponse["date"].orEmpty(),
        no2 = previsionResponse.levelOf("no2"),
        o3 = previsionResponse.levelOf("o3"),
        pm10 = previsionResponse.levelOf("pm10"),
        pm25 = previsionResponse.levelOf("pm25"),
        so2 = previsionResponse.levelOf("so2"),
        indice = previsionResponse.levelOf("indice")
    )

private fun Map<String, String>.levelOf(pollutant: String): AirParifPrevisionEnum =
    AirParifPrevisionEnum.getEnumFromValue(this[pollutant].orEmpty())
