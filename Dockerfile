FROM gradle:8.7.0-jdk17-alpine AS build

WORKDIR /dash-webservices
COPY build.gradle.kts .
COPY ./src ./src
RUN gradle assemble


FROM eclipse-temurin:17.0.11_9-jre-alpine
WORKDIR /dash-webservices
EXPOSE 8080
RUN adduser --system --no-create-home dockeruser

COPY --from=build /dash-webservices/build/libs/dash-webservices-*.jar /dash-webservices/dash-webservices.jar
ARG SPRING_PROFILE
COPY ./src/main/resources/application.yml application.yml

USER dockeruser
ENTRYPOINT ["java","-Dspring.profiles.active=${SPRING_PROFILE:dev}", "-jar", "dash-webservices.jar"]