package com.dash.domain.mapping

import com.dash.domain.model.airParif.AirParifColors
import com.dash.domain.model.airParif.AirParifPrevisionEnum
import com.dash.domain.model.airParif.Prevision
import org.springframework.stereotype.Component

@Component
class AirParifApiResponseToAirParifDomain {

    fun airParifColorsResponseToAirParifColorsDomain(colorsResponse: LinkedHashMap<String, String>): AirParifColors =
        AirParifColors(
            colorsResponse.getOrDefault("Bon", ""),
            colorsResponse.getOrDefault("Moyen", ""),
            colorsResponse.getOrDefault("Dégradé", ""),
            colorsResponse.getOrDefault("Mauvais", ""),
            colorsResponse.getOrDefault("Très Mauvais", ""),
            colorsResponse.getOrDefault("Extrêmement Mauvais", "")
        )

    fun airParifPrevisionsResponseToAirParifDomain(
        communeInseeCode: String,
        airParifPrevisionResponse: LinkedHashMap<String, List<LinkedHashMap<String, String>>>
    ): List<Prevision> =
        airParifPrevisionResponse.getOrDefault(communeInseeCode, listOf(LinkedHashMap())).map { previsionResponseToPrevisionDomain(it) }

    private fun previsionResponseToPrevisionDomain(previsionResponse: LinkedHashMap<String, String>): Prevision =
        Prevision(
            previsionResponse.getOrDefault("date", ""),
            AirParifPrevisionEnum.getEnumFromValue(previsionResponse.getOrDefault("no2", AirParifPrevisionEnum.MISSING.prevision)),
            AirParifPrevisionEnum.getEnumFromValue(previsionResponse.getOrDefault("o3", AirParifPrevisionEnum.MISSING.prevision)),
            AirParifPrevisionEnum.getEnumFromValue(previsionResponse.getOrDefault("pm10", AirParifPrevisionEnum.MISSING.prevision)),
            AirParifPrevisionEnum.getEnumFromValue(previsionResponse.getOrDefault("pm25", AirParifPrevisionEnum.MISSING.prevision)),
            AirParifPrevisionEnum.getEnumFromValue(previsionResponse.getOrDefault("so2", AirParifPrevisionEnum.MISSING.prevision)),
            AirParifPrevisionEnum.getEnumFromValue(previsionResponse.getOrDefault("indice", AirParifPrevisionEnum.MISSING.prevision))
        )
}
