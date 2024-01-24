FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-11-jdk -y

ENV POSTGRES_USER=postgres.iltokvkzkyazwrfbwcjc
ENV SERVER_PORT=80
ENV POSTGRES_HOST=jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:6543/postgres
ENV POSTGRES_PASS=@RethinkPoints2024

COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:11-jdk-slim
COPY --from=build target/RethinkPoints-0.0.1-SNAPSHOT.jar RethinkPoints.jar
EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "-jar", "RethinkPoints.jar"]