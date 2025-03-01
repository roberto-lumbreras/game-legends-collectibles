FROM maven:3.9.9-eclipse-temurin-21 as build

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build app/target/game-legends-collectibles-0.0.1-SNAPSHOT.jar game-legends-collectibles-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD [ "java", "-jar", "game-legends-collectibles-0.0.1-SNAPSHOT.jar" ]