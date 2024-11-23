FROM eclipse-temurin:21.0.5_11-jdk-alpine AS build

ENV GRADLE_OPTS="-Xmx512m"

WORKDIR /build-step

COPY ./src ./src
COPY gradlew gradlew
COPY ./gradle ./gradle
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts

RUN chmod +x gradlew \
    && ./gradlew assemble


FROM eclipse-temurin:23.0.1_11-jre-noble AS run

COPY --from=build /build-step/build/libs/dash-webservices-*.jar dash-webservices.jar

RUN adduser --system --no-create-home dockeruser
USER dockeruser

ARG SPRING_PROFILE
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILE:default}", "-jar", "dash-webservices.jar"]
