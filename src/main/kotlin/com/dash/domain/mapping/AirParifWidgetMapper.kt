package com.dash.domain.mapping

import com.dash.domain.model.airParif.AirParifColor
import com.dash.domain.model.airParif.AirParifPrevisionEnum
import com.dash.domain.model.airParif.Prevision
import org.springframework.stereotype.Component

@Component
class AirParifWidgetMapper {
    fun colorsResponseToDomain(colorsResponse: LinkedHashMap<String, String>): List<AirParifColor> =
        listOf(
            AirParifColor(AirParifPrevisionEnum.BON, colorsResponse.getOrDefault(AirParifPrevisionEnum.BON.prevision, "")),
            AirParifColor(AirParifPrevisionEnum.MOYEN, colorsResponse.getOrDefault(AirParifPrevisionEnum.MOYEN.prevision, "")),
            AirParifColor(AirParifPrevisionEnum.DEGRADE, colorsResponse.getOrDefault(AirParifPrevisionEnum.DEGRADE.prevision, "")),
            AirParifColor(AirParifPrevisionEnum.MAUVAIS, colorsResponse.getOrDefault(AirParifPrevisionEnum.MAUVAIS.prevision, "")),
            AirParifColor(AirParifPrevisionEnum.TRES_MAUVAIS, colorsResponse.getOrDefault(AirParifPrevisionEnum.TRES_MAUVAIS.prevision, "")),
            AirParifColor(AirParifPrevisionEnum.EXTREMEMENT_MAUVAIS, colorsResponse.getOrDefault(AirParifPrevisionEnum.EXTREMEMENT_MAUVAIS.prevision, ""))
        )

    fun previsionsResponseToDomain(
        communeInseeCode: String,
        airParifPrevisionResponse: LinkedHashMap<String, List<LinkedHashMap<String, String>>>
    ): List<Prevision> = airParifPrevisionResponse.getOrDefault(communeInseeCode, listOf(LinkedHashMap())).map {
        previsionResponseToDomain(it)
    }

    private fun previsionResponseToDomain(previsionResponse: LinkedHashMap<String, String>): Prevision =
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
