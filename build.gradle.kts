import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun
import io.gitlab.arturbosch.detekt.Detekt

val kotlinVersion = "1.6.21"
val springBootVersion = "2.7.0"
val jwtVersion = "0.9.1"
val ical4jVersion = "3.2.2"

val jacksonModuleKotlinVersion = "2.13.3"
val jacksonDatatypeVersion = "2.13.3"
val jacksonModuleJaxbVersion = "2.13.3"
val jacksonDataformatVersion = "2.13.3"
val log4jVersion = "2.17.2"

val liquibaseVersion = "4.11.0"
val postgresqlVersion = "42.3.6"
val gsonVersion = "2.9.0"

val restAssuredVersion = "4.5.1"
val mockitoKotlinVersion = "4.0.0"
val junitVersion = "5.8.2"
val hibernateTypesVersion = "2.16.2"
val testContainersVersion = "1.17.2"

val detektVersion = "1.20.0"
val ktlintVersion = "0.45.2"

val ktlint: Configuration by configurations.creating

plugins {
    val kotlinVersion = "1.7.0"
    val springBootVersion = "2.7.0"
    val springDependencyManagementVersion = "1.0.11.RELEASE"
    val codacyPluginVersion = "0.1.0"
    val detektVersion = "1.20.0"

    jacoco
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion
    id("io.github.ddimtirov.codacy") version codacyPluginVersion
    id("io.gitlab.arturbosch.detekt") version detektVersion
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
}

group = "com.dash"
version = "0.2.0"
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
    implementation ("org.springframework.boot:spring-boot-starter-security:$springBootVersion")

    implementation ("io.jsonwebtoken:jjwt:$jwtVersion")
    implementation("org.mnode.ical4j:ical4j:$ical4jVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonModuleKotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    implementation("com.fasterxml.jackson.module:jackson-modules-base:$jacksonModuleJaxbVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonDatatypeVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonDataformatVersion")
    implementation ("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")

    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("com.vladmihalcea:hibernate-types-52:$hibernateTypesVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")

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
    environment("spring.profiles.active", "test")
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
