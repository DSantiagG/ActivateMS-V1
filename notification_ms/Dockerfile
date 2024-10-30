# Usar la imagen de OpenJDK como base
FROM openjdk:21-jdk-slim

# Definir el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR generado por Maven o Gradle al contenedor
COPY target/notification_ms-0.0.1-SNAPSHOT.jar recommendation_ms_app.jar

# Exponer el puerto en el que corre la app (por defecto 8080)
EXPOSE 8083

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "recommendation_ms_app.jar"]
