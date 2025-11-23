FROM eclipse-temurin:21
WORKDIR /app

COPY bareun_ai_intermediate.pem /tmp/bareun_ai_intermediate.pem
RUN keytool -import -trustcacerts \
    -alias bareun_ai_cert \
    -file /tmp/bareun_ai_intermediate.pem \
    -keystore $JAVA_HOME/lib/security/cacerts \
    -storepass changeit \
    -noprompt
RUN rm /tmp/bareun_ai_intermediate.pem

COPY build/libs/widdle-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]
CMD ["--spring.profiles.active=prod"]
