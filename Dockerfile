FROM gradle:8.8.0-jdk21-alpine AS build

WORKDIR /dash-webservices
COPY build.gradle.kts .
COPY ./src ./src
RUN gradle assemble


FROM eclipse-temurin:21.0.3_9-jre-alpine
WORKDIR /dash-webservices
EXPOSE 8080
RUN adduser --system --no-create-home dockeruser

ARG SPRING_PROFILE
COPY --from=build /dash-webservices/build/libs/dash-webservices-*.jar /dash-webservices/dash-webservices.jar
COPY ./src/main/resources/application.yml application.yml

USER dockeruser
ENTRYPOINT ["java","-Dspring.profiles.active=${SPRING_PROFILE:default}", "-jar", "dash-webservices.jar"]