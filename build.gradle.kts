plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.springdoc.openapi)
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.kover)
    alias(libs.plugins.sonarqube)
}

group = "com.dash"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter.webmvc)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.liquibase)
    implementation(libs.spring.boot.starter.restclient)

    implementation(libs.spring.cloud.gcp.starter.secretmanager)

    implementation(libs.jjwt)
    implementation(libs.ical4j)
    implementation(libs.kotlin.reflect)

    implementation(libs.jackson.module.kotlin)
    implementation(libs.jackson.dataformat.xml)
    implementation(libs.springdoc.openapi.starter.webmvc.api)

    runtimeOnly(libs.postgresql)

    testImplementation(libs.spring.boot.starter.security.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.rest.assured)
    testImplementation(libs.rest.assured.kotlin.extensions)
    testRuntimeOnly(libs.junit.platform.launcher)
}

// Overrides of versions managed by the Spring Boot and GCP BOMs, for security fixes they don't include yet
extra["jackson-bom.version"] = libs.versions.jackson.get()
extra["jackson-2-bom.version"] = libs.versions.jackson2.get()
extra["logback.version"] = libs.versions.logback.get()

dependencyManagement {
    imports {
        mavenBom(libs.spring.cloud.gcp.dependencies.get().toString())
        mavenBom(libs.spring.cloud.dependencies.get().toString())
    }
    dependencies {
        dependency(libs.guava.get().toString())
    }
}

// The Spring Cloud BOMs pin an older Spring Boot: keep every Spring Boot module on the plugin's version
configurations.configureEach {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.springframework.boot") {
            useVersion(libs.versions.spring.boot.get())
        }
    }
}

sonar {
    properties {
        property("sonar.projectName", "Dash-WebServices")
        property("sonar.projectKey", "ArnaudFlaesch_Dash-WebServices")
        property("sonar.organization", "arnaudflaesch")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.qualitygate.wait", true)
        property("sonar.sources", "src/main")
        // The SQL scripts target PostgreSQL, which Sonar would otherwise analyze as Oracle PL/SQL
        property("sonar.exclusions", "**/*.sql")
        property("sonar.tests", "src/test/kotlin")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/kover/report.xml")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
}

openApi {
    apiDocsUrl.set("http://localhost:8080/api-docs")
    // Startup (Liquibase, Hibernate) can exceed the default 30 seconds on CI runners
    waitTimeInSeconds.set(120)
    customBootRun {
        args.set(listOf("--spring.profiles.active=test"))
    }
}

// The springdoc plugin's tasks, which fork the application, don't support the configuration cache
tasks.matching { it.name in setOf("forkedSpringBootRun", "forkedSpringBootStop", "generateOpenApiDocs") }.configureEach {
    notCompatibleWithConfigurationCache("springdoc-openapi-gradle-plugin does not support the configuration cache")
}

tasks.withType<Test> {
    environment("spring.profiles.active", "test")
    // Mockito's agent appends to the bootstrap classpath, which disables class data sharing and logs a JVM warning
    jvmArgs("-XX:+EnableDynamicAgentLoading", "-Xshare:off")
    useJUnitPlatform()
}

tasks.jar {
    enabled = false
}
