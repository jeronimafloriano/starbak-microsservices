FROM maven:3.8.5-openjdk-18 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:18
WORKDIR /app
COPY --from=build ./app/target/*.jar ./app.jar

ARG RABBITMQ_SERVER=rabbitmq-host
ARG EUREKA_SERVER=localhost

ENTRYPOINT java -jar -Dspring.profiles.active=produtction app.jar