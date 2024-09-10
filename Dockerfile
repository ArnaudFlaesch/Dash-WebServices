FROM gradle:8.10.1-jdk21-alpine AS build

ENV GRADLE_OPTS="-Xmx512m"

WORKDIR /build-step

COPY ./src ./src
COPY gradlew gradlew
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts
RUN gradle assemble --no-daemon


FROM eclipse-temurin:21.0.4_7-jre-alpine AS run

COPY --from=build /build-step/build/libs/dash-webservices-*.jar dash-webservices.jar

RUN adduser --system --no-create-home dockeruser
USER dockeruser

EXPOSE 8080
ARG SPRING_PROFILE
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILE:default}", "-jar", "dash-webservices.jar"]