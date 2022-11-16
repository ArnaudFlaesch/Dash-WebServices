FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080

ADD ./build/libs/dash-webservices-*.jar dash-webservices.jar
ADD ./src/main/resources/application.properties application.properties
CMD ["java","-Dspring.profiles.active=dev", "-Dspring.config.location=./application.properties", "-jar", "dash-webservices.jar"]