# --- Etapa 1: Build con JDK 24 + Maven ---
FROM openjdk:24-jdk-slim AS build
# Instalamos Maven dentro del contenedor
RUN apt-get update && apt-get install -y --no-install-recommends maven && rm -rf /var/lib/apt/lists/*
WORKDIR /app

# Cache de dependencias
COPY pom.xml .
RUN mvn -q -B dependency:resolve dependency:resolve-plugins

# Código fuente
COPY src ./src

# Compilar SIN tocar tu pom (usa JDK 24, respeta <java.version>24)
RUN mvn -q -B clean package -DskipTests

# --- Etapa 2: Runtime con JDK 24 (puede ser slim también) ---
FROM openjdk:24-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV TZ=America/Lima
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
