import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun
import io.gitlab.arturbosch.detekt.Detekt

val kotlinVersion = "1.3.72"
val springBootVersion = "2.3.0.RELEASE"

plugins {
    jacoco
    id("org.springframework.boot") version "2.3.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("com.github.kt3k.coveralls") version "2.10.1"
    id("io.github.ddimtirov.codacy") version "0.1.0"
    id("io.gitlab.arturbosch.detekt") version "1.9.1"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
    kotlin("plugin.jpa") version "1.3.72"
}
group = "com.dash"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_13

repositories {
    jcenter {
        content {
            // just allow to include kotlinx projects
            // detekt needs 'kotlinx-html' for the html report
            includeGroup("org.jetbrains.kotlinx")
        }
    }
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.fasterxml.jackson.module:jackson-module-jaxb-annotations")
    implementation("org.liquibase:liquibase-core")

    implementation("org.apache.logging.log4j:log4j-api")
    implementation("org.postgresql:postgresql:42.2.13")
    implementation("com.vladmihalcea:hibernate-types-52:2.9.11")
    implementation("com.h2database:h2")

    testImplementation("io.rest-assured:rest-assured:4.2.0")
    testImplementation("io.rest-assured:json-path:4.2.0")
    testImplementation("io.rest-assured:xml-path:4.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

coveralls {
    sourceDirs.add("src/main/kotlin")
}

detekt {
    failFast = false // fail build on any finding
    buildUponDefaultConfig = true // preconfigure defaults
    config = files("$projectDir/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior

    reports {
        html.enabled = true // observe findings in your browser with structure and code snippets
        xml.enabled = true // checkstyle like format mainly for integrations like Jenkins
        txt.enabled = true // similar to the console output, contains issue signature to manually edit baseline files
    }
}

tasks.withType<Detekt> {
    this.jvmTarget = "13"
}

tasks.withType<JacocoReport> {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}

tasks.withType<BootRun> {
    systemProperties(System.getProperties().mapKeys { it.key as String })
}

tasks.withType<Test> {
    environment("spring.profiles.active", "test")
    environment("spring.config.location", "src/test/resources/application-test.properties")
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "13"
    }
}