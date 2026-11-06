FROM eclipse-temurin:25.0.5_8-jdk-alpine-3.24@sha256:c7c3d0bdf7f2bbc5a05a6712ed334d198360105634ea9adf76fca94f381b1e3f AS build

ENV GRADLE_OPTS="-Xmx512m"

WORKDIR /build-step

COPY ./src ./src
COPY gradlew gradlew
COPY ./gradle ./gradle
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts

RUN chmod +x gradlew \
    && ./gradlew assemble

FROM eclipse-temurin:25.0.5_8-jre-alpine-3.24@sha256:e1898fd6a11fc965a994d8ace186fd3585aeb636cf8f05f47a723fcfee0b0918 AS run

COPY --from=build /build-step/build/libs/dash-webservices-*.jar dash-webservices.jar

RUN adduser --system --no-create-home dockeruser
USER dockeruser
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "dash-webservices.jar"]
