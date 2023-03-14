FROM openjdk:19
COPY . /app
WORKDIR /app
CMD ["java", "-jar", "target/spring-boot-docker.jar"]
