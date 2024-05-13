FROM maven:3.6.3-openjdk-17 AS build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
RUN mvn clean package
FROM openjdk:17
COPY --from=build target/user_management-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "user_management-0.0.1-SNAPSHOT.jar"]
