# Step Build project
FROM maven:3.8.4-openjdk-11 AS build
COPY . .
RUN mvn clean install

# Step Run Application
FROM openjdk:11-jdk-slim
COPY --from=build target/hogwartsPoints-0.0.1-SNAPSHOT.jar hogwartsPoints-0.0.1-SNAPSHOT.jar
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-jar", "hogwartsPoints-0.0.1-SNAPSHOT.jar"]
