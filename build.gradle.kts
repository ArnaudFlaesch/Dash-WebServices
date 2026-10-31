val kotlinVersion = "2.4.21"
val springBootVersion = "4.1.2"
val jwtVersion = "0.13.0"
val ical4jVersion = "4.4.0"

val springDocVersion = "3.2.0"
val jacksonModuleVersion = "3.2.4"
val jackson2Version = "2.22.4"
val logbackVersion = "1.6.6"
val guavaVersion = "33.7.2-jre"

val postgresqlVersion = "42.7.14"

val restAssuredVersion = "6.3.0"
val mockitoKotlinVersion = "6.4.0"

val springCloudGcpVersion = "8.3.1"
val springCloudVersion = "2025.1.4"

plugins {
    val kotlinPluginVersion = "2.4.10"
    val springBootPluginVersion = "4.1.2"
    val springDocGradlePluginVersion = "1.9.0"
    val springDependencyManagementPluginVersion = "1.1.7"
    val kotlinterPluginVersion = "5.7.0"
    val sonarQubePluginVersion = "7.5.0.8588"
    val koverPluginVersion = "0.9.11"

    kotlin("jvm") version kotlinPluginVersion
    kotlin("plugin.spring") version kotlinPluginVersion
    kotlin("plugin.jpa") version kotlinPluginVersion

    id("org.springframework.boot") version springBootPluginVersion
    id("io.spring.dependency-management") version springDependencyManagementPluginVersion
    id("org.springdoc.openapi-gradle-plugin") version springDocGradlePluginVersion
    id("org.jmailen.kotlinter") version kotlinterPluginVersion
    id("org.jetbrains.kotlinx.kover") version koverPluginVersion
    id("org.sonarqube") version sonarQubePluginVersion
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
    implementation("org.springframework.boot:spring-boot-starter-webmvc:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-security:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-actuator:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-liquibase:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-restclient:$springBootVersion")

    implementation("com.google.cloud:spring-cloud-gcp-starter-secretmanager:$springCloudGcpVersion")

    implementation("io.jsonwebtoken:jjwt:$jwtVersion")
    implementation("org.mnode.ical4j:ical4j:$ical4jVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    implementation("tools.jackson.module:jackson-module-kotlin:$jacksonModuleVersion")
    implementation("tools.jackson.dataformat:jackson-dataformat-xml:$jacksonModuleVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:$springDocVersion")

    runtimeOnly("org.postgresql:postgresql:$postgresqlVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-security-test:$springBootVersion")

    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlinVersion")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("io.rest-assured:kotlin-extensions:$restAssuredVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// Overrides of versions managed by the Spring Boot and GCP BOMs, for security fixes they don't include yet
extra["jackson-bom.version"] = jacksonModuleVersion
extra["jackson-2-bom.version"] = jackson2Version
extra["logback.version"] = logbackVersion

dependencyManagement {
    imports {
        mavenBom("com.google.cloud:spring-cloud-gcp-dependencies:$springCloudGcpVersion")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
    dependencies {
        dependency("com.google.guava:guava:$guavaVersion")
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
