package com.dash.domain.mapping

import com.dash.domain.model.airParif.AirParifColor
import com.dash.domain.model.airParif.AirParifPrevisionEnum
import com.dash.domain.model.airParif.Prevision
import org.springframework.stereotype.Component

@Component
class AirParifWidgetMapper {
    fun colorsResponseToDomain(colorsResponse: Map<String, String>): List<AirParifColor> =
        AirParifPrevisionEnum
            .entries
            .filter { it != AirParifPrevisionEnum.MISSING }
            .map { AirParifColor(it, colorsResponse[it.prevision].orEmpty()) }

    fun previsionsResponseToDomain(
        communeInseeCode: String,
        airParifPrevisionResponse: Map<String, List<Map<String, String>>>
    ): List<Prevision> =
        airParifPrevisionResponse
            .getOrDefault(communeInseeCode, listOf(emptyMap()))
            .map(::previsionResponseToDomain)

    private fun previsionResponseToDomain(previsionResponse: Map<String, String>): Prevision {
        fun pollutant(name: String) = AirParifPrevisionEnum.getEnumFromValue(previsionResponse[name].orEmpty())

        return Prevision(
            previsionResponse["date"].orEmpty(),
            pollutant("no2"),
            pollutant("o3"),
            pollutant("pm10"),
            pollutant("pm25"),
            pollutant("so2"),
            pollutant("indice")
        )
    }
}
