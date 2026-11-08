package com.dash

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

/**
 * Every bounded context (dashboard, cashmanager, shared) lives under "com.dash", so component, entity, repository
 * and configuration properties scanning is limited to the application's own classes by default.
 */
@SpringBootApplication
@ConfigurationPropertiesScan
class DashWebServicesApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<DashWebServicesApplication>(*args)
}
