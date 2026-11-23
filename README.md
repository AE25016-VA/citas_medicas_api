# API REST - Sistema de Gestión de Citas Médicas

Proyecto desarrollado para la asignatura de **Programación Orientada a Objetos** (Ciclo II/2026 - Universidad de El Salvador, Facultad Multidisciplinaria de Occidente)[cite: 2]. Consiste en una API RESTful funcional construida con Java y Spring Boot para la automatización, centralización y optimización del flujo transaccional de consultas médicas, coordinando pacientes, médicos, horarios y especialidades bajo una arquitectura escalable, contenerizada y automatizada mediante CI/CD[cite: 2].

---

## Integrantes del Equipo

| Nombre Completo | Carnet / Código |
| :--- | :--- |
| Cesia Mariena Alfaro Hernández | AH23007 |
| Vanesa Gabriela Arevalo Elias | AE25016 |
| Liliana Melissa Cruz Henriquez | CH20014 |
| Gerson Antonio Chámul Ramírez | CR25082 |
| Cristian Mauricio Molina Monzon | MM18192 |

---

## Arquitectura, Modelado e Ingeniería de Datos

El sistema fue diseñado e implementado siguiendo una arquitectura en capas (`Controller`, `Service`, `Repository`), desacoplando las entidades de persistencia mediante el patrón **DTO (Data Transfer Object)**, validaciones de entrada y manejo centralizado de excepciones (`GlobalExceptionHandler`) para garantizar respuestas HTTP consistentes (`200`, `201`, `400`, `404`, `500`)[cite: 2, 7, 9].

### Artefactos de Diseño
1. **Diagrama de Casos de Uso (UML):** Delimita el alcance funcional del sistema y la interacción de los actores principales (Paciente, Médico y Administrador).
2. **Modelo Entidad-Relación (DER Conceptual):** Establece la estructura de información, reglas de negocio y cardinalidades del dominio (`1:N` y `N:M`).
3. **Diagrama Relacional (Base de Datos):** Traducción física del modelo conceptual a PostgreSQL, definiendo claves primarias (PK), claves foráneas (FK) y tablas intermedias.
4. **Diagrama de Clases (UML):** Define la arquitectura orientada a objetos del backend, especificando atributos, visibilidad y métodos de negocio clave.

### Entidades Principales
* **Paciente:** Administra el perfil, datos de contacto y credenciales de los usuarios solicitantes de citas.
* **Medico:** Gestiona el perfil profesional del personal de salud encargado de las consultas.
* **Especialidad / Especialidad_medico:** Catálogo de áreas médicas y tabla asociativa para soportar múltiples especialidades por médico.
* **HorarioMedico:** Controla los días, turnos y rangos de disponibilidad de los médicos mediante lógica de validación de horarios (`consultarDisponibilidad()`).
* **Cita:** Núcleo transaccional del sistema que vincula al paciente, médico y especialidad con su respectivo estado, fecha y hora.

---

## Stack Tecnológico Implementado

* **Lenguaje y Framework:** Java 17 con Spring Boot (Spring Web, Spring Data JPA, Spring Validation)[cite: 2]
* **Gestor de Dependencias:** Apache Maven (con wrapper `./mvnw` incluido)[cite: 2, 7]
* **Utilidades y Patrones:** Lombok, DTOs y Manejo Global de Excepciones[cite: 2, 7]
* **Motor de Base de Datos:** PostgreSQL
* **Pruebas Unitarias:** JUnit 5 y Mockito[cite: 2]
* **Contenerización y Orquestación:** Docker y Kubernetes (`Docker Desktop` / `kind`)
* **Automatización CI/CD:** GitHub Actions ejecutado localmente mediante `act` y `KUBECONFIG`
* **Pruebas y Documentación de API:** Postman y Swagger / OpenAPI
* **Entorno de Desarrollo y Diseño:** Visual Studio Code y Draw.io

---

## Trabajo Realizado en el Proyecto

1. **Desarrollo del Backend y Persistencia:** Implementación completa de los endpoints CRUD (`GET`, `POST`, `PUT`, `DELETE`) para todas las entidades del modelo y sus relaciones en PostgreSQL.
2. **Calidad de Código y Pruebas:** Implementación de pruebas unitarias automatizadas con JUnit y Mockito sobre la capa de servicio, junto con validaciones en DTOs y manejo centralizado de errores[cite: 2, 7, 9].
3. **Contenerización (`Dockerfile`):** Creación de la imagen Docker (`citas-medicas-api:1.0`) que empaqueta el artefacto `.jar` compilado por Maven para ejecutarse de forma aislada y portable[cite: 2].
4. **Orquestación en Kubernetes (`k8s/`):** Definición de los manifiestos `deployment.yaml` y `service.yaml` para desplegar el contenedor en un clúster local de Kubernetes utilizando `imagePullPolicy: Never` y conexión externa hacia PostgreSQL mediante variables de entorno y `host.docker.internal`[cite: 9].
5. **Pipeline CI/CD Automatizado (`.github/workflows/ci-cd.yml`):** Configuración del flujo continuo (Build, Test, Docker Build, carga de imagen y despliegue en Kubernetes) ejecutado en entorno local mediante `act` y enlazado al clúster a través de `.kube-local/config`[cite: 2, 9].

---

## Requisitos Previos para la Ejecución

Antes de ejecutar el proyecto en un entorno local (Windows, macOS o Linux), verifique que cuenta con:

1. **Java JDK 17** o superior.
2. **PostgreSQL** activo en el puerto `5432` con la base de datos `citas_medicas_db` creada.
3. **Docker** instalado y en ejecución (Docker Desktop en Windows/macOS o Docker Engine en Linux).
4. **Clúster local de Kubernetes activo:**
   * **Windows / macOS:** Habilitar Kubernetes en *Docker Desktop > Settings > Kubernetes > Enable Kubernetes*[cite: 2].
   * **Linux:** Tener creado el clúster local con `kind` (`kind create cluster --name desktop`).
5. **`kubectl`** instalado y conectado al clúster (`kubectl get nodes` debe responder en estado `Ready`).
6. **`act`** instalado para ejecutar el flujo de GitHub Actions de manera local[cite: 2, 9].

---

## Guía de Instalación y Ejecución

### Paso 1: Preparar la Base de Datos Local
Inicie el servicio de PostgreSQL y cree la base de datos correspondiente:

```sql
CREATE DATABASE citas_medicas_db;