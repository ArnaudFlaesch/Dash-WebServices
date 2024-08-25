package com.dash.domain.mapping

import com.dash.domain.model.airParif.AirParifColor
import com.dash.domain.model.airParif.AirParifPrevisionEnum
import com.dash.domain.model.airParif.Prevision
import org.springframework.stereotype.Component

@Component
class AirParifWidgetMapper {
    fun colorsResponseToDomain(colorsResponse: LinkedHashMap<String, String>): List<AirParifColor> =
        AirParifPrevisionEnum
            .entries
            .filter { it != AirParifPrevisionEnum.MISSING }
            .map { AirParifColor(it, colorsResponse.getOrDefault(it.prevision, "")) }

    fun previsionsResponseToDomain(
        communeInseeCode: String,
        airParifPrevisionResponse: LinkedHashMap<String, List<LinkedHashMap<String, String>>>
    ): List<Prevision> =
        airParifPrevisionResponse
            .getOrDefault(communeInseeCode, listOf(LinkedHashMap()))
            .map(this::previsionResponseToDomain)

    private fun previsionResponseToDomain(previsionResponse: LinkedHashMap<String, String>): Prevision {
        val getEnumFromValue = { prevision: String ->
            AirParifPrevisionEnum.getEnumFromValue(previsionResponse.getOrDefault(prevision, ""))
        }

        return Prevision(
            previsionResponse.getOrDefault("date", ""),
            getEnumFromValue("no2"),
            getEnumFromValue("o3"),
            getEnumFromValue("pm10"),
            getEnumFromValue("pm25"),
            getEnumFromValue("so2"),
            getEnumFromValue("indice")
        )
    }
}
