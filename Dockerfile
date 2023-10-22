FROM maven:latest

WORKDIR /app

COPY . /app

RUN mvn clean install

FROM openjdk:17

COPY --from=0 /app/target/*.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]