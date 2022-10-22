FROM openjdk:20-ea-16-slim-bullseye
EXPOSE 8080

ADD ./build/libs/dash-webservices-*.jar dash-webservices.jar
ARG PROPERTIES_FILE
ARG SPRING_PROFILE
ADD ./src/main/resources/application.properties application-.properties
ADD ./src/main/resources/application-prod.properties application-prod.properties
CMD ["java","-Dspring.profiles.active=${SPRING_PROFILE:-dev}", "-Dspring.config.location=./application.properties", "-jar", "dash-webservices.jar"]