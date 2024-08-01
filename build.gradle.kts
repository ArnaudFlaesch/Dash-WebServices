val kotlinVersion = "2.0.0"
val springBootVersion = "3.3.2"
val jwtVersion = "0.12.6"
val ical4jVersion = "4.0.2"

val springDocVersion = "2.6.0"
val jacksonVersion = "2.17.2"
val log4jVersion = "2.23.1"

val liquibaseVersion = "4.29.1"
val postgresqlVersion = "42.7.3"
val gsonVersion = "2.11.0"

val springSecurityVersion = "6.3.1"
val restAssuredVersion = "5.5.0"
val mockitoKotlinVersion = "5.4.0"
val junitPlatformLauncherVersion = "1.10.3"
val hibernateTypesVersion = "2.21.1"
val testContainersVersion = "1.20.1"

plugins {
    val kotlinPluginVersion = "2.0.0"
    val springBootPluginVersion = "3.3.2"
    val springDependencyManagementPluginVersion = "1.1.6"
    val kotlinterPluginVersion = "4.4.1"

    jacoco
    id("org.springframework.boot") version springBootPluginVersion
    id("io.spring.dependency-management") version springDependencyManagementPluginVersion
    // id("org.springdoc.openapi-gradle-plugin") version springDocGradlePluginVersion
    id("org.jmailen.kotlinter") version kotlinterPluginVersion
    kotlin("jvm") version kotlinPluginVersion
    kotlin("plugin.spring") version kotlinPluginVersion
    kotlin("plugin.jpa") version kotlinPluginVersion
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

val springCloudGcpVersion = "5.5.0"
val springCloudVersion = "2023.0.3"

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

    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("com.vladmihalcea:hibernate-types-60:$hibernateTypesVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitPlatformLauncherVersion")

    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlinVersion")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("io.rest-assured:json-path:$restAssuredVersion")
    testImplementation("io.rest-assured:xml-path:$restAssuredVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation("org.springframework.security:spring-security-test:$springSecurityVersion")
    testImplementation("org.springframework.boot:spring-boot-testcontainers:$springBootVersion")

    testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
    testImplementation("org.testcontainers:postgresql:$testContainersVersion")
    testImplementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocVersion")
}

dependencyManagement {
    imports {
        mavenBom("com.google.cloud:spring-cloud-gcp-dependencies:$springCloudGcpVersion")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.withType<Test> {
    environment("spring.profiles.active", "test")
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}