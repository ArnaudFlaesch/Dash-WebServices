package com.dash.service

import com.dash.model.airParif.Prevision
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import org.springframework.util.MultiValueMapAdapter
import java.util.*
import kotlin.collections.LinkedHashMap

@Service
class AirParifWidgetService {

    private val AIRPARIF_API_URL = "https://api.airparif.asso.fr/indices/prevision"
    private val AIRPARIF_API_INSEE_ENDPOINTS = "$AIRPARIF_API_URL/commune"
    private val AIRPARIF_API_COLORS_ENDPOINTS = "$AIRPARIF_API_URL/couleurs"

    private val APIKEY = "1dfea964-b7ab-a47c-3602-ee56d6603217"

    private val logger = LoggerFactory.getLogger(this::class.java.name)

    @Autowired
    private lateinit var proxyService: ProxyService

    fun getPrevisionCommune(communeInseeCode: String): ResponseEntity<LinkedHashMap<String, List<Prevision>>> /*Map<String, Any> */ {
        val url = "$AIRPARIF_API_INSEE_ENDPOINTS?insee=$communeInseeCode"

        val requestHeaders = HttpHeaders()
        requestHeaders.accept = Collections.singletonList(MediaType.APPLICATION_JSON)
        requestHeaders.set("X-Api-Key", APIKEY)
        val httpEntity: HttpEntity<LinkedHashMap<String, List<Prevision>>> = HttpEntity<LinkedHashMap<String, List<Prevision>>>(requestHeaders)
        return proxyService.getDataFromProxy(url, httpEntity)
    }
}
