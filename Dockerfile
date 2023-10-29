FROM maven:3.9.5-eclipse-temurin-17-alpine

WORKDIR /app

COPY . /app

RUN mvn clean install

FROM openjdk:17-jdk-alpine3.14

COPY --from=0 /app/target/*.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]