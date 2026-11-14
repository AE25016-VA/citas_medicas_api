# Contenerización con Docker

La API de **Gestión de Citas Médicas** (`citas_medicas_api`) se encuentra contenerizada utilizando un enfoque de **construcción multietapa (Multi-stage build)** para optimizar el tamaño final de la imagen y separar el entorno de compilación del de ejecución.

# Archivos de Configuración
* **`Dockerfile`**: Ubicado en el directorio del proyecto Maven (`citas_medicas_api/Dockerfile`).
  1. **Etapa de compilación (`build`)**: Utiliza la imagen `maven:3.9.6-eclipse-temurin-17` para descargar las dependencias y empaquetar el código fuente en un archivo ejecutable `.jar` (omitiendo pruebas unitarias).
  2. **Etapa de ejecución (`run`)**: Utiliza una imagen ligera `eclipse-temurin:17-jre` que copia únicamente el `.jar` generado y expone el puerto `8080`.
* **`.dockerignore`**: Excluye carpetas locales y archivos innecesarios (`target/`, `.git/`, `.vscode/`, `.mvn/`, etc.) para acelerar la construcción y reducir el peso del contexto de Docker.

# Comandos de Uso

**1. Construir la imagen:**
Desde la carpeta que contiene el `pom.xml` y el `Dockerfile`, ejecutar:
```bash
docker build -t citas-medicas-api:1.0 .