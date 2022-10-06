package com.dash.controller

import com.dash.service.AirParifWidgetService
import com.dash.service.RssWidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/airParifWidget", produces = [ MediaType.APPLICATION_JSON_VALUE ])
class AirParifWidgetController {

    @Autowired
    private lateinit var airParifWidgetService: AirParifWidgetService

    @GetMapping("/previsionCommune")
    fun getPrevisionCommune(@RequestParam(value = "commune") commune: String): Any =
        airParifWidgetService.getPrevisionCommune(commune)

  /*  @GetMapping("/couleurs")
    fun getAirParifCouleurs(): Any =
        airParifWidgetService.getJsonFeedFromUrl(url) */
}
