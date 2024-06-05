FROM eclipse-temurin:21_35-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./build/libs/user_management-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9004
CMD ["java", "-jar", "app.jar"]
