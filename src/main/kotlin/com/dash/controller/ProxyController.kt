package com.dash.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/proxy")
class ProxyController {

    private val logger = LoggerFactory.getLogger(this::class.java.name)

    @GetMapping("/")
    fun getUrlFromProxy(@RequestParam(value = "url") url: String): Any? {
        val client = HttpClient.newHttpClient()
        return try {
            val request = HttpRequest.newBuilder()
                .uri(URI.create(url
                    .replace(" ", "%20")
                    .replace("#", "%23")
                    .replace("@", "%40")
                ).normalize())
                .build()
            client.send(request, HttpResponse.BodyHandlers.ofString()).body()
        } catch (error: Exception) {
            logger.error(error.message + " " + url)
            throw Error("Mauvaise URL $url")
        }
    }
}
