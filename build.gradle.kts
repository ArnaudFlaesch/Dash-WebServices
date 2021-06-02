import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

val kotlinVersion = "1.5.10"
val springBootVersion = "2.5.0"
val jwtVersion = "0.9.1"

val jacksonModuleKotlinVersion = "2.12.3"
val jacksonModuleJaxbVersion = "2.12.3"
val log4jVersion = "2.14.1"

val liquibaseVersion = "4.3.5"
val postgresqlVersion = "42.2.20"
val gsonVersion = "2.8.7"

val restAssuredVersion = "4.4.0"
val junitVersion = "5.7.2"
val hibernateTypesVersion = "2.11.1"
val ktlintVersion = "0.41.0"

val ktlint: Configuration by configurations.creating

plugins {
    val kotlinVersion = "1.5.10"
    val springBootVersion = "2.4.4"
    val springDependencyManagementVersion = "1.0.11.RELEASE"
    val codacyPluginVersion = "0.1.0"

    jacoco
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion
    id("io.github.ddimtirov.codacy") version codacyPluginVersion
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
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonModuleKotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    implementation("com.fasterxml.jackson.module:jackson-module-jaxb-annotations:$jacksonModuleJaxbVersion")
    implementation ("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")

    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("com.vladmihalcea:hibernate-types-52:$hibernateTypesVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")

    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("io.rest-assured:json-path:$restAssuredVersion")
    testImplementation("io.rest-assured:xml-path:$restAssuredVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    ktlint("com.pinterest:ktlint:${ktlintVersion}")
}

jacoco {
    toolVersion = "0.8.7"
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (this.requested.group == "org.codehaus.groovy") {
            this.useVersion("3.0.2")
            this.because("needed by rest-assured>=4.3")
        }
    }
}

tasks.jacocoTestReport {
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
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "16"
    }
}

val outputDir = "${project.buildDir}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlintCheck by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Check Kotlin code style."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("src/**/*.kt")
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("-F", "src/**/*.kt")
}