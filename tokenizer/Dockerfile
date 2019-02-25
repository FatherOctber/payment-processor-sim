FROM openjdk:8-jdk-alpine
MAINTAINER Mishnev Nikita <fatheroctober@gmail.com>
VOLUME /tmp
ARG JAR_FILE=target/tokenizer-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} tokenizer.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tokenizer.jar"]