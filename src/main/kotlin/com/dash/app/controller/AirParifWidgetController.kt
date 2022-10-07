package com.dash.app.controller

import com.dash.domain.model.airParif.AirParifColors
import com.dash.domain.model.airParif.Prevision
import com.dash.domain.service.AirParifWidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/airParifWidget", produces = [MediaType.APPLICATION_JSON_VALUE])
class AirParifWidgetController {

    @Autowired
    private lateinit var airParifWidgetService: AirParifWidgetService

    @GetMapping("/previsionCommune")
    fun getPrevisionCommune(@RequestParam(value = "commune") commune: String): List<Prevision> =
        airParifWidgetService.getPrevisionCommune(commune)

    @GetMapping("/couleurs")
    fun getAirParifCouleurs(): AirParifColors =
        airParifWidgetService.getColors()
}
