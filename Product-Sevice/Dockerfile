FROM ubuntu:latest
FROM maven:3.8.4-openjdk-17 AS build
LABEL authors="PC_Tarik"
WORKDIR /app
COPY . .
RUN mvn clean package
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/product-service.jar product-service.jar
EXPOSE 8031
ENTRYPOINT ["java", "-jar", "product-service.jar"]