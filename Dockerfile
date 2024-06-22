FROM gradle:8.8.0-jdk21-alpine AS build

WORKDIR /dash-webservices
ENV GRADLE_OPTS="-Xmx512m"
COPY build.gradle.kts .
COPY ./src ./src
RUN gradle assemble --no-daemon


FROM eclipse-temurin:21.0.3_9-jre-alpine AS run
WORKDIR /dash-webservices
EXPOSE 8080
RUN adduser --system --no-create-home dockeruser

ARG SPRING_PROFILE
COPY --from=build /dash-webservices/build/libs/dash-webservices-*.jar /dash-webservices/dash-webservices.jar
COPY --from=build /dash-webservices/src/main/resources/application.yml /dash-webservices/application.yml

USER dockeruser
ENTRYPOINT ["java","-Dspring.profiles.active=${SPRING_PROFILE:default}", "-jar", "dash-webservices.jar"]