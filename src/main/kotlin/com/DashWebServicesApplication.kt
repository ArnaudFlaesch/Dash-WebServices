package com

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType

@SpringBootApplication
@ComponentScan(
    excludeFilters = [
        ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = [
                "com.google.cloud.spring.autoconfigure.datastore.*",
                "com.google.cloud.datastore.*",
                "com.google.cloud.spring.autoconfigure.spanner.*",
                "com.google.cloud.spanner.*"
            ]
        )
    ]
)
class DashWebServicesApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<DashWebServicesApplication>(*args)
}
