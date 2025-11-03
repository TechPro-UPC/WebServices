# --- Etapa 1: Build del proyecto con Maven ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia el descriptor Maven y descarga dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el código fuente
COPY src ./src

# Empaqueta el .jar (sin correr tests para acelerar)
RUN mvn clean package -DskipTests

# --- Etapa 2: Imagen final más ligera ---
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiamos el jar generado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Variable de entorno (opcional)
ENV TZ=America/Lima

# Exponemos el puerto donde corre el servicio (ajusta según el tuyo)
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
