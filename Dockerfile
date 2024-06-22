FROM gradle:8.8.0-jdk21-alpine AS build

WORKDIR /build-step-dir
ENV GRADLE_OPTS="-Xmx512m"
COPY build.gradle.kts .
COPY ./src ./src
RUN gradle assemble --no-daemon


FROM eclipse-temurin:21.0.3_9-jre-alpine
WORKDIR /dist-step-dir
EXPOSE 8080
RUN adduser --system --no-create-home dockeruser

ARG SPRING_PROFILE
COPY --from=build /build-step-dir/build/libs/dash-webservices-*.jar /dist-step-dir/dash-webservices.jar
COPY ./src/main/resources/application.yml application.yml

USER dockeruser
ENTRYPOINT ["java","-Dspring.profiles.active=${SPRING_PROFILE:default}", "-jar", "dash-webservices.jar"]