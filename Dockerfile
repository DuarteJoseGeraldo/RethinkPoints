FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-11-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:11-jdk-slim
COPY --from=build target/RethinkPoints-0.0.1-SNAPSHOT.jar RethinkPoints.jar
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-jar", "RethinkPoints.jar"]