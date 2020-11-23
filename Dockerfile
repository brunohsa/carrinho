# Dockerfile
FROM openjdk:11-jre

RUN mkdir app

ADD target/carrinho-1.0-SNAPSHOT.jar app/carrinho.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=docker", "app/carrinho.jar"]