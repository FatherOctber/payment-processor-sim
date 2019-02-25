FROM openjdk:8-jdk-alpine
MAINTAINER Mishnev Nikita <fatheroctober@gmail.com>
VOLUME /tmp
ARG JAR_FILE=target/consumer-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} consumer.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/consumer.jar"]