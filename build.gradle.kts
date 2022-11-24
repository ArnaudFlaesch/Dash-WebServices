import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun
import io.gitlab.arturbosch.detekt.Detekt

val kotlinVersion = "1.5.10"
val springBootVersion = "2.7.5"
val jwtVersion = "0.9.1"
val ical4jVersion = "3.2.7"

val springDocVersion = "1.6.13"
val jacksonVersion = "2.14.1"
val log4jVersion = "2.19.0"

val liquibaseVersion = "4.17.2"
val postgresqlVersion = "42.5.1"
val gsonVersion = "2.10"

val restAssuredVersion = "4.5.1"
val mockitoKotlinVersion = "4.0.0"
val junitVersion = "5.9.1"
val hibernateTypesVersion = "2.20.0"
val testContainersVersion = "1.17.6"

val detektVersion = "1.19.0"
val ktlintVersion = "0.47.1"

val ktlint: Configuration by configurations.creating

plugins {
    val kotlinVersion = "1.5.10"
    val springBootVersion = "2.7.5"
    val springDependencyManagementVersion = "1.1.0"
    val codacyPluginVersion = "0.1.0"
    val detektVersion = "1.19.0"
    val springDocGradlePluginVersion = "1.5.0"

    jacoco
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion
    id ("org.springdoc.openapi-gradle-plugin") version springDocGradlePluginVersion
    id("io.github.ddimtirov.codacy") version codacyPluginVersion
    id("io.gitlab.arturbosch.detekt") version detektVersion
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
}

group = "com.dash"
version = "0.4.0"
java.sourceCompatibility = JavaVersion.VERSION_16

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
    implementation ("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    implementation ("org.springframework.boot:spring-boot-starter-security:$springBootVersion")

    implementation ("org.springdoc:springdoc-openapi-ui:$springDocVersion")

    implementation ("io.jsonwebtoken:jjwt:$jwtVersion")
    implementation("org.mnode.ical4j:ical4j:$ical4jVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    implementation("com.fasterxml.jackson.module:jackson-modules-base:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")

    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("com.vladmihalcea:hibernate-types-52:$hibernateTypesVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")

    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("io.rest-assured:json-path:$restAssuredVersion")
    testImplementation("io.rest-assured:xml-path:$restAssuredVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
    testImplementation("org.testcontainers:postgresql:$testContainersVersion")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
    ktlint("com.pinterest:ktlint:${ktlintVersion}")
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

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
        jvmTarget = "16"
    }
}

tasks.getByName<Jar>("jar") {
    isEnabled = false
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "16"
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

openApi {
    customBootRun {
        args.set(listOf("--spring.config.location=src/test/resources/application-test.properties"))
    }
}

val ktLintOutputDir = "${project.buildDir}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlintCheck by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(ktLintOutputDir)

    description = "Check Kotlin code style."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("src/**/*.kt")
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(ktLintOutputDir)

    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("-F", "src/**/*.kt")
}
