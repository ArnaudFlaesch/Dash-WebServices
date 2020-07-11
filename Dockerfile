FROM openjdk:13-jdk-alpine
EXPOSE 8080
COPY ./build/libs/dash-webservices-*.jar /app/dash-webservices.jar
WORKDIR /app
CMD ["java", "-jar", "dash-webservices.jar"]