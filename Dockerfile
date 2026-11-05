FROM eclipse-temurin:25.0.5_8-jdk-alpine-3.24@sha256:425f0b733cb23843bd78dbe5552513976d0cf12b9722752c48a5161cf5cd2f32 AS build

ENV GRADLE_OPTS="-Xmx512m"

WORKDIR /build-step

COPY ./src ./src
COPY gradlew gradlew
COPY ./gradle ./gradle
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts

RUN chmod +x gradlew \
    && ./gradlew assemble

FROM eclipse-temurin:25.0.5_8-jre-alpine-3.24@sha256:56f273059de6b13a6b49fe0ded691ed2d0399bbee76156a8f2d385f7524a3d43 AS run

COPY --from=build /build-step/build/libs/dash-webservices-*.jar dash-webservices.jar

RUN adduser --system --no-create-home dockeruser
USER dockeruser
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "dash-webservices.jar"]
