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

    private fun previsionResponseToDomain(previsionResponse: LinkedHashMap<String, String>): Prevision =
        Prevision(
            previsionResponse.getOrDefault("date", ""),
            AirParifPrevisionEnum.getEnumFromValue(previsionResponse["no2"]),
            AirParifPrevisionEnum.getEnumFromValue(previsionResponse["o3"]),
            AirParifPrevisionEnum.getEnumFromValue(previsionResponse["pm10"]),
            AirParifPrevisionEnum.getEnumFromValue(previsionResponse["pm25"]),
            AirParifPrevisionEnum.getEnumFromValue(previsionResponse["so2"]),
            AirParifPrevisionEnum.getEnumFromValue(previsionResponse["indice"])
        )
}
