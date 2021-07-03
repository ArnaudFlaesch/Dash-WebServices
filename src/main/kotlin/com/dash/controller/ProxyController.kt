package com.dash.controller

import com.dash.exceptions.ConnectionErrorException
import com.dash.exceptions.WrongUrlException
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.io.IOException
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
                .uri(
                    URI.create(
                        url.replace(" ", "%20")
                            .replace("#", "%23")
                            .replace("@", "%40")
                    ).normalize()
                )
                .build()
            client.send(request, HttpResponse.BodyHandlers.ofString()).body()
        } catch (error: IOException) {
            logger.error(error.message + " " + url)
            throw WrongUrlException("Mauvaise URL $url")
        } catch (error: InterruptedException) {
            logger.error(error.message + " " + url)
            throw ConnectionErrorException("Problème lors de l'envoi de la requête.")
        }
    }
}
