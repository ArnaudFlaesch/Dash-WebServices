FROM gradle:7.5.1-jdk17-alpine as build

WORKDIR /dash-webservices
COPY build.gradle.kts .
COPY ./src ./src
RUN gradle assemble


FROM eclipse-temurin:17-jre-alpine
WORKDIR /dash-webservices
EXPOSE 8080

COPY --from=build /dash-webservices/build/libs/dash-webservices-*.jar /dash-webservices/dash-webservices.jar
ARG SPRING_PROFILE
ARG FILE_CONTEXT
ADD ./src/main/resources/application.properties application.properties
ADD ./src/main/resources/application-prod.properties application-prod.properties
CMD ["java","-Dspring.profiles.active=${SPRING_PROFILE:dev}", "-Dspring.config.location=./${FILE_CONTEXT:application}.properties", "-jar", "dash-webservices.jar"]