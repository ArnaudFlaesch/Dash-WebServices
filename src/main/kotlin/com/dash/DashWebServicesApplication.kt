package com.dash

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DashWebServicesApplication

fun main(args: Array<String>) {
	runApplication<DashWebServicesApplication>(*args)
}
