FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

RUN adduser -D -u 1000 myuser && \
    chown -R myuser:myuser /app

RUN apk add --no-cache bash && \
    rm -rf /var/cache/apk/*

COPY --chown=myuser:myuser . .

USER myuser

RUN ./gradlew clean build -x test --no-daemon && \
    rm -rf /app/.gradle

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/build/libs/highload-1.0.jar"]