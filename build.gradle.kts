import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

val kotlinVersion = "1.4.32"
val springBootVersion = "2.4.4"
val jacksonModuleKotlinVersion = "2.12.3"
val jacksonModuleJaxbVersion = "2.12.2"
val log4jVersion = "2.14.1"
val liquibaseVersion = "4.3.3"
val postgresqlVersion = "42.2.19"
val restAssuredVersion = "4.2.0"
val junitVersion = "5.7.1"
val hibernateTypesVersion = "2.10.4"
val ktlintVersion = "0.41.0"

val ktlint: Configuration by configurations.creating

plugins {
    val kotlinVersion = "1.4.32"
    val springBootVersion = "2.4.4"
    val springDependencyManagementVersion = "1.0.11.RELEASE"
    val coverallsPluginVersion = "2.12.0"
    val codacyPluginVersion = "0.1.0"

    jacoco
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion
    id("com.github.kt3k.coveralls") version coverallsPluginVersion
    id("io.github.ddimtirov.codacy") version codacyPluginVersion
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
}
group = "com.dash"
version = "0.2.0"
java.sourceCompatibility = JavaVersion.VERSION_15

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
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonModuleKotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    implementation("com.fasterxml.jackson.module:jackson-module-jaxb-annotations:$jacksonModuleJaxbVersion")
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")

    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("com.vladmihalcea:hibernate-types-52:$hibernateTypesVersion")

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

coveralls {
    sourceDirs.add("src/main/kotlin")
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
    dependsOn(tasks.test) // tests are required to run before generating the report
}

tasks.withType<BootRun> {
    systemProperties(System.getProperties().mapKeys { it.key as String })
}

tasks.withType<Test> {
    environment("spring.profiles.active", "test")
    environment("spring.config.location", "src/test/resources/application-test.properties")
    useJUnitPlatform()
    configure<JacocoTaskExtension> {
        excludes = listOf("com/dash/DashWebServicesApplication.kt")
    }
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "15"
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