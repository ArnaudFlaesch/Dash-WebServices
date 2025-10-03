FROM eclipse-temurin:25-jdk-noble AS build

ENV GRADLE_OPTS="-Xmx512m"

WORKDIR /build-step

COPY ./src ./src
COPY gradlew gradlew
COPY ./gradle ./gradle
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts

RUN chmod +x gradlew \
    && ./gradlew assemble

FROM eclipse-temurin:25-jre-noble AS run

COPY --from=build /build-step/build/libs/dash-webservices-*.jar dash-webservices.jar

RUN adduser --system --no-create-home dockeruser
USER dockeruser
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "dash-webservices.jar"]
