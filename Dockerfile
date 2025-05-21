# Etapa de construcción
FROM gradle:8.4-jdk21 AS build
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle build --no-daemon

# Etapa de ejecución (runtime)
FROM eclipse-temurin:21-jdk-alpine
EXPOSE 8080
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]