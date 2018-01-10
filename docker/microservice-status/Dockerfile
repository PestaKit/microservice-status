FROM openjdk:8-jdk-alpine
VOLUME /tmp
RUN apk add --no-cache bash
COPY wait_for_it.sh /opt/
ADD swagger-spring-1.0.0.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
