FROM eclipse-temurin:25.0.5.1_2-jdk-alpine-3.24@sha256:22edbb1021420ae6e248519d1af07577742314d67d4c60fcbb2e285b7e47fa78 AS build

ENV GRADLE_OPTS="-Xmx512m"

WORKDIR /build-step

# Download the Gradle distribution in its own layer, so it is cached until the wrapper changes
COPY gradlew ./
COPY gradle ./gradle
RUN chmod +x gradlew \
    && ./gradlew --no-daemon --version

COPY settings.gradle.kts build.gradle.kts gradle.properties ./
COPY src ./src
RUN ./gradlew --no-daemon assemble

FROM eclipse-temurin:25.0.5.1_2-jre-alpine-3.24@sha256:69c2a26e9894016e274c880fa542956d005b3755f84db765145185e87405bd89 AS run

WORKDIR /app

RUN adduser --system --no-create-home dockeruser

COPY --from=build /build-step/build/libs/dash-webservices-*.jar dash-webservices.jar

USER dockeruser
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "dash-webservices.jar"]
