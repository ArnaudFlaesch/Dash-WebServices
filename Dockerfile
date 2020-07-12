FROM openjdk:13-jdk-alpine
EXPOSE 8080
ADD ./build/libs/dash-webservices-*.jar dash-webservices.jar
ADD ./src/test/resources/application-test.properties application-test.properties
CMD ["java","-Dspring.active.profile=test", "-Dspring.config.location=./application-test.properties", "-jar", "dash-webservices.jar"]