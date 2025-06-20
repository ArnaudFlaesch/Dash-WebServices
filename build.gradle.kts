val kotlinVersion = "2.1.21"
val springBootVersion = "3.5.3"
val jwtVersion = "0.12.6"
val ical4jVersion = "4.1.1"

val springDocVersion = "2.8.9"
val jacksonVersion = "2.19.1"
val log4jVersion = "2.25.0"

val liquibaseVersion = "4.32.0"
val postgresqlVersion = "42.7.7"
val gsonVersion = "2.13.1"

val springSecurityVersion = "6.5.1"
val restAssuredVersion = "5.5.5"
val mockitoKotlinVersion = "5.4.0"
val junitPlatformLauncherVersion = "1.12.2"
val hibernateTypesVersion = "2.21.1"

val springCloudGcpVersion = "6.2.2"
val springCloudVersion = "2025.0.0"

plugins {
    val kotlinPluginVersion = "2.1.21"
    val springBootPluginVersion = "3.5.3"
    val springDocGradlePluginVersion = "1.9.0"
    val springDependencyManagementPluginVersion = "1.1.7"
    val kotlinterPluginVersion = "5.1.1"
    val sonarQubePluginVersion = "6.2.0.5505"
    val koverPluginVersion = "0.9.1"

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
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-security:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-actuator:$springBootVersion")

    implementation("com.google.cloud:spring-cloud-gcp-starter-secretmanager:$springCloudGcpVersion")

    implementation("io.jsonwebtoken:jjwt:$jwtVersion")
    implementation("org.mnode.ical4j:ical4j:$ical4jVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-modules-base:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:$springDocVersion")

    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("com.vladmihalcea:hibernate-types-60:$hibernateTypesVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation("org.springframework.security:spring-security-test:$springSecurityVersion")

    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlinVersion")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("io.rest-assured:kotlin-extensions:$restAssuredVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitPlatformLauncherVersion")
}

dependencyManagement {
    imports {
        mavenBom("com.google.cloud:spring-cloud-gcp-dependencies:$springCloudGcpVersion")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
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
        property("sonar.tests", "src/test/kotlin")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/kover/report.xml")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

openApi {
    apiDocsUrl.set("http://localhost:8080/api-docs")
    customBootRun {
        args.set(listOf("--spring.profiles.active=test"))
    }
}

tasks.withType<Test> {
    environment("spring.profiles.active", "test")
    jvmArgs("-XX:+EnableDynamicAgentLoading")
    useJUnitPlatform()
}
