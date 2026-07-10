val kotlinVersion = "2.4.0"
val springBootVersion = "4.0.6"
val jwtVersion = "0.13.0"
val ical4jVersion = "4.3.0"

val springDocVersion = "3.0.3"
val jacksonModuleVersion = "3.2.1"
val log4jVersion = "2.26.1"

val postgresqlVersion = "42.7.13"
val gsonVersion = "2.14.0"

val restAssuredVersion = "6.0.0"
val mockitoKotlinVersion = "6.3.0"
val junitPlatformLauncherVersion = "6.0.0"
val hibernateTypesVersion = "3.15.4"

val springCloudGcpVersion = "8.0.5"
val springCloudVersion = "2025.1.2"

plugins {
    val kotlinPluginVersion = "2.4.0"
    val springBootPluginVersion = "4.0.6"
    val springDocGradlePluginVersion = "1.9.0"
    val springDependencyManagementPluginVersion = "1.1.7"
    val kotlinterPluginVersion = "5.6.0"
    val sonarQubePluginVersion = "7.3.1.8318"
    val koverPluginVersion = "0.9.8"

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
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webmvc:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-security:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-actuator:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-liquibase:$springBootVersion")

    implementation("com.google.cloud:spring-cloud-gcp-starter-secretmanager:$springCloudGcpVersion")

    implementation("io.jsonwebtoken:jjwt:$jwtVersion")
    implementation("org.mnode.ical4j:ical4j:$ical4jVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    implementation("tools.jackson.module:jackson-module-kotlin:$jacksonModuleVersion")
    implementation("tools.jackson.dataformat:jackson-dataformat-xml:$jacksonModuleVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:$springDocVersion")

    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("io.hypersistence:hypersistence-utils-hibernate-73:$hibernateTypesVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-security-test:$springBootVersion")

    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlinVersion")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("io.rest-assured:kotlin-extensions:$restAssuredVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("com.google.cloud:spring-cloud-gcp-dependencies:$springCloudGcpVersion")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
    dependencies {
        dependency("org.apache.tomcat.embed:tomcat-embed-core:11.0.22")
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
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
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
