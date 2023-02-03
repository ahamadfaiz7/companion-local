FROM docker.io/library/openjdk:17-alpine
# FROM openjdk:17-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /app
VOLUME /tmp
COPY build/libs/*-SNAPSHOT.jar /app/app.jar

EXPOSE 5004

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
