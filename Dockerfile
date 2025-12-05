
FROM gradle:8.8-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle build -x test

# Etapa 2: Ejecución
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]
