FROM ubuntu:latest
LABEL authors="PC_ANASS"
FROM maven:4.0.0-jdk-21-alim
COPY src /A/src
COPY pom.xml /A
RUN mvn -f /A/ppm.xml
ENTRYPOINT ["top", "-b"]