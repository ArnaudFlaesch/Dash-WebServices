FROM openjdk:18-ea-22-jdk-oracle
EXPOSE 8080

ADD ./build/libs/dash-webservices-*.jar dash-webservices.jar
ADD ./src/test/resources/application-testdocker.properties application-testdocker.properties
CMD ["java","-Dspring.profiles.active=testdocker", "-Dspring.config.location=./application-testdocker.properties", "-jar", "dash-webservices.jar"]