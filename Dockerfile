FROM openjdk:13-jdk-alpine
EXPOSE 8080
COPY ./build/libs/dash-webservices-*.jar dash-webservices.jar
CMD ["java", "-jar", "dash-webservices.jar"]