FROM eclipse-temurin:25.0.5_8-jdk-alpine-3.24@sha256:c7c3d0bdf7f2bbc5a05a6712ed334d198360105634ea9adf76fca94f381b1e3f AS build

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

FROM eclipse-temurin:25.0.5_8-jre-alpine-3.24@sha256:e1898fd6a11fc965a994d8ace186fd3585aeb636cf8f05f47a723fcfee0b0918 AS run

WORKDIR /app

RUN adduser --system --no-create-home dockeruser

COPY --from=build /build-step/build/libs/dash-webservices-*.jar dash-webservices.jar

USER dockeruser
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "dash-webservices.jar"]
