FROM gradle:8.4.0-jdk17-alpine AS build

WORKDIR /dash-webservices
COPY build.gradle.kts .
COPY ./src ./src
RUN gradle assemble


FROM eclipse-temurin:21.0.1_12-jre-alpine
WORKDIR /dash-webservices
EXPOSE 8080
RUN adduser --system --no-create-home dockeruser

COPY --from=build /dash-webservices/build/libs/dash-webservices-*.jar /dash-webservices/dash-webservices.jar
ARG SPRING_PROFILE
ARG FILE_CONTEXT
ADD ./src/main/resources/application.properties application.properties
ADD ./src/main/resources/application-prod.properties application-prod.properties

USER dockeruser
ENTRYPOINT ["java","-Dspring.profiles.active=${SPRING_PROFILE:dev}", "-Dspring.config.location=./${FILE_CONTEXT:application}.properties", "-jar", "dash-webservices.jar"]