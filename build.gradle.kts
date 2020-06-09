import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

val kotlinVersion = "1.3.72"
val springBootVersion = "2.3.0.RELEASE"

plugins {
    jacoco
    id("org.springframework.boot") version "2.3.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("com.github.kt3k.coveralls") version "2.10.1"
    id("io.github.ddimtirov.codacy") version "0.1.0"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
    kotlin("plugin.jpa") version "1.3.72"
}
group = "com.dash"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_13

repositories {
    jcenter()
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
    maven {
        url = uri("http://repo.typesafe.com/typesafe/releases/com/typesafe/netty/netty-http-pipelining/")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.h2database:h2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.json:json:20200518")
    implementation("org.postgresql:postgresql:42.2.13")
    implementation("com.vladmihalcea:hibernate-types-52:2.9.11")
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

tasks.withType<JacocoReport> {
    reports {
        xml.setEnabled(true)
        html.setEnabled(true)
    }
}

tasks.withType<BootRun> {
    environment("spring.config.location", "src/main/kotlin/com/dash/resources/application.properties")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "13"
    }
}