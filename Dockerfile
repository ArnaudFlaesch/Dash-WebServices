FROM gradle:6.3.0-jdk13
EXPOSE 8080
COPY . /src
WORKDIR /src
RUN gradle build -x test --no-daemon
CMD ["java", "-jar", "build/libs/dash-webservices-0.0.1.jar"]