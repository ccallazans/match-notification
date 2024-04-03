FROM maven:3.8.5-openjdk-17-slim AS build

WORKDIR /app

COPY . .

RUN mvn clean package

FROM openjdk:17-jdk-alpine3.14

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
