import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

val kotlinVersion = "1.9.10"
val springBootVersion = "3.1.3"
val jwtVersion = "0.9.1"
val ical4jVersion = "3.2.13"

val springDocVersion = "1.7.0"
val jacksonVersion = "2.15.2"
val log4jVersion = "2.20.0"

val liquibaseVersion = "4.23.2"
val postgresqlVersion = "42.6.0"
val gsonVersion = "2.10.1"

val springSecurityVersion = "6.1.3"
val restAssuredVersion = "5.3.2"
val mockitoKotlinVersion = "5.1.0"
val junitVersion = "5.10.0"
val hibernateTypesVersion = "2.21.1"
val testContainersVersion = "1.19.0"

val detektVersion = "1.23.1"

plugins {
    val kotlinPluginVersion = "1.9.0"
    val springBootPluginVersion = "3.1.3"
    val springDependencyManagementPluginVersion = "1.1.3"
    val detektPluginVersion = "1.23.1"
    val kotlinterPluginVersion = "3.16.0"
    val springDocGradlePluginVersion = "1.5.0"

    jacoco
    id("org.springframework.boot") version springBootPluginVersion
    id("io.spring.dependency-management") version springDependencyManagementPluginVersion
    // id("org.springdoc.openapi-gradle-plugin") version springDocGradlePluginVersion
    id("io.gitlab.arturbosch.detekt") version detektPluginVersion
    id("org.jmailen.kotlinter") version kotlinterPluginVersion
    kotlin("jvm") version kotlinPluginVersion
    kotlin("plugin.spring") version kotlinPluginVersion
    kotlin("plugin.jpa") version kotlinPluginVersion
}

group = "com.dash"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17

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

    implementation("org.springdoc:springdoc-openapi-ui:$springDocVersion")

    implementation("io.jsonwebtoken:jjwt:$jwtVersion")
    implementation("org.mnode.ical4j:ical4j:$ical4jVersion") {
        exclude("org.codehaus.groovy", "groovy")
    }
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    implementation("com.fasterxml.jackson.module:jackson-modules-base:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")

    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("com.vladmihalcea:hibernate-types-60:$hibernateTypesVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")

    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("io.rest-assured:json-path:$restAssuredVersion")
    testImplementation("io.rest-assured:xml-path:$restAssuredVersion")

    testImplementation("org.springframework.security:spring-security-test:$springSecurityVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
    testImplementation("org.testcontainers:postgresql:$testContainersVersion")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}
/**
tasks.create("bootRunMainClassName") {
dependsOn(tasks.resolveMainClassName)
}
 */

tasks.withType<BootRun> {
    systemProperties(System.getProperties().mapKeys { it.key as String })
}

tasks.withType<Test> {
    environment("spring.config.location", "src/test/resources/application-test.properties")
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.getByName<Jar>("jar") {
    isEnabled = false
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "17"
    reports {
        html.required.set(true) // observe findings in your browser with structure and code snippets
        xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
        txt.required.set(true) // similar to the console output, contains issue signature to manually edit baseline files
        sarif.required.set(true) // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with Github Code Scanning
    }
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
    config = files(file("$projectDir/detekt.yml"))
    autoCorrect = true
}

/*
openApi {
    customBootRun {
        args.set(listOf("--spring.config.location=src/test/resources/application-test.properties"))
    }
}
*/