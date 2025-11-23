FROM eclipse-temurin:21
WORKDIR /app
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]
CMD ["--spring.profiles.active=prod"]
