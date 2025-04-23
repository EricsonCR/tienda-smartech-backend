# Etapa 1: Construcción del proyecto
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución optimizada
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copiar el archivo JAR desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto en el que la app correrá
EXPOSE 8080

# Configurar el contenedor para ejecutar la aplicación Spring Boot usando las variables de entorno
ENTRYPOINT ["java", "-jar", "app.jar"]
