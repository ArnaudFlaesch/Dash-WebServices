FROM gradle:6.3.0-jdk13
EXPOSE 8080
RUN gradle clean build
ADD ./build/libs/dash-webservices-*.jar dash-webservices.jar
CMD ["java", "-jar", "dash-webservices.jar"]