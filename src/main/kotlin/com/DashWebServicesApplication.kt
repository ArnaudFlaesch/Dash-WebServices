package com

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

// The application lives in the "com" package, so scanning is limited to its own packages
// to keep third-party libraries (e.g. com.google.cloud) out of component, entity and repository scanning.
private const val DASH_PACKAGE = "com.dash"
private const val COMMON_PACKAGE = "com.common"
private const val CASHMANAGER_PACKAGE = "com.cashmanager"

@SpringBootApplication(scanBasePackages = [DASH_PACKAGE, COMMON_PACKAGE, CASHMANAGER_PACKAGE])
@EntityScan(DASH_PACKAGE, COMMON_PACKAGE, CASHMANAGER_PACKAGE)
@EnableJpaRepositories(DASH_PACKAGE, COMMON_PACKAGE, CASHMANAGER_PACKAGE)
class DashWebServicesApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<DashWebServicesApplication>(*args)
}
