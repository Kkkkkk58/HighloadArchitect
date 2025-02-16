FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

RUN adduser -D -u 1000 myuser && \
    chown -R myuser:myuser /app

RUN apk add --no-cache bash && \
    rm -rf /var/cache/apk/*

COPY --chown=myuser:myuser gradlew /app/gradlew
COPY --chown=myuser:myuser gradle /app/gradle
COPY --chown=myuser:myuser build.gradle.kts /app/build.gradle.kts
COPY --chown=myuser:myuser settings.gradle.kts /app/settings.gradle.kts

USER myuser

RUN ./gradlew dependencies --no-daemon

COPY --chown=myuser:myuser src /app/src

RUN ./gradlew clean build -x test --no-daemon && \
    rm -rf /app/.gradle

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /app/build/libs/highload-1.0.jar /app/build/libs/highload-1.0.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/build/libs/highload-1.0.jar"]