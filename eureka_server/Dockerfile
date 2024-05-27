FROM maven:3.6.3-openjdk-17 AS build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
RUN mvn clean install package
FROM openjdk:17
COPY --from=build target/eureka_server-0.0.1-SNAPSHOT.jar .
EXPOSE 8761
CMD ["java", "-jar", "eureka_server-0.0.1-SNAPSHOT.jar"]