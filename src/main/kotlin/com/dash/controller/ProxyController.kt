package com.dash.controller

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
@CrossOrigin( origins = ["*"])
@RequestMapping("/proxy")
class ProxyController {
    
    @GetMapping("/")
    fun getUrlFromProxy(@RequestParam(value = "url") url: String): Any {
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
                .uri(URI.create(url.replace(" ", "%20")).normalize())
                .build()
        return(client.send (request, HttpResponse.BodyHandlers.ofString()).body())
    }
}
