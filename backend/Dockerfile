# syntax=docker/dockerfile:1.7

FROM gradle:8.14.3-jdk21 AS build
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

COPY src ./src
RUN --mount=type=cache,target=/home/gradle/.gradle,sharing=locked \
    gradle bootJar --no-daemon --max-workers=1 \
    -Dorg.gradle.jvmargs="-Xms512m -Xmx1536m -XX:MaxMetaspaceSize=512m" \
    -Pspring.profiles.active=prod


# ---- Base Image ----
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

RUN useradd --create-home --shell /usr/sbin/nologin appuser
USER appuser

# ---- Application ----
COPY --from=build /app/build/libs/widdle-0.0.1-SNAPSHOT.jar app.jar

# ---- Runtime ----
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]
CMD ["--spring.profiles.active=prod"]
