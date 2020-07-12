FROM openjdk:13-jdk-alpine
EXPOSE 8080
RUN ./gradlew clean build
ADD ./build/libs/dash-webservices-*.jar dash-webservices.jar
CMD ["java", "-jar", "dash-webservices.jar"]