# Etapa 1: Construcción (Build)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Copiamos el pom.xml y descargamos dependencias primero (mejora el caché de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline
# Copiamos el código fuente y compilamos
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Run)
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copiamos el .jar generado en la etapa anterior
COPY --from=build /app/target/citas_medicas_api-0.0.1-SNAPSHOT.jar app.jar
# Exponemos el puerto por defecto de Spring Boot
EXPOSE 8080
# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]