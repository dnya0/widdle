FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY src ./src
RUN gradle build -x test --no-daemon -Pspring.profiles.active=prod


# ---- Base Image ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# ---- Application ----
COPY build/libs/widdle-0.0.1-SNAPSHOT.jar app.jar

# ---- Runtime ----
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]
CMD ["--spring.profiles.active=prod"]
