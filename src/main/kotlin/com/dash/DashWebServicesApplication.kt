package com.dash

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.CrossOrigin

@SpringBootApplication
@CrossOrigin(origins = ["*"])
class DashWebServicesApplication

fun main(args: Array<String>) {
    runApplication<DashWebServicesApplication>(*args)
}
