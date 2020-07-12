FROM openjdk:13-jdk-alpine
EXPOSE 8080
ADD ./build/libs/dash-webservices-*.jar dash-webservices.jar
CMD ["java", "-jar", "dash-webservices.jar", "-Dspring.active.profile=test", "-Dspring.config.location=src/test/resources/application-test.properties"]