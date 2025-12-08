# Etapa 1: build
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

# ✅ DAR PERMISO DE EJECUCIÓN
RUN chmod +x gradlew

RUN ./gradlew build -x test

EXPOSE 9090

CMD ["java","-jar","build/libs/mil_sabores-0.0.1-SNAPSHOT.jar"]
