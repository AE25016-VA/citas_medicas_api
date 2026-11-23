# API REST - Sistema de Gestión de Citas Médicas

Proyecto desarrollado para la asignatura de Programación Orientada a Objetos (Ciclo II/2026 - Universidad de El Salvador, Facultad Multidisciplinaria de Occidente). Consiste en una API RESTful construida con Spring Boot para administrar el flujo de citas médicas, aplicando estándares de desarrollo en capas, contenerización con Docker, orquestación en Kubernetes y automatización de integración y despliegue continuo (CI/CD) mediante `act`.

---

## Arquitectura y Tecnologías Utilizadas

* **Lenguaje y Framework:** Java 17 / Spring Boot (Spring Web, Spring Data JPA, Spring Validation)[cite: 2]
* **Base de Datos:** PostgreSQL
* **Gestor de Dependencias:** Maven
* **Patrones y Utilidades:** Arquitectura en capas (`Controller`, `Service`, `Repository`), patrón DTO, Lombok y manejo centralizado de excepciones (`GlobalExceptionHandler`)[cite: 2, 7, 9]
* **Pruebas Unitarias:** JUnit 5 y Mockito[cite: 2]
* **Contenerización y Orquestación:** Docker y Kubernetes (`Docker Desktop` / `kind`)
* **Automatización CI/CD:** GitHub Actions ejecutado localmente con `act` y `KUBECONFIG`[cite: 2, 9]

---

## Requisitos Previos

Para ejecutar este proyecto de forma local y reproducir el flujo completo de CI/CD y Kubernetes, es necesario contar con los siguientes componentes instalados y activos:

1. **Java JDK 17** o superior y **Maven** (o uso del wrapper `./mvnw` incluido en el proyecto).
2. **PostgreSQL** en ejecución en el puerto `5432`, con una base de datos creada llamada `citas_medicas_db`.
3. **Docker** en ejecución (Docker Desktop en Windows/macOS, o Docker Engine en Linux).
4. **Clúster local de Kubernetes activo:**
   * En Windows/macOS: Habilitar Kubernetes desde *Docker Desktop > Settings > Kubernetes > Enable Kubernetes*[cite: 2].
   * En Linux: Tener creado el clúster local mediante `kind` (por ejemplo: `kind create cluster --name desktop`).
5. **`kubectl`** instalado y apuntando al clúster local (`kubectl get nodes` debe mostrar el nodo en estado `Ready`).
6. **`act`** instalado para la ejecución local del pipeline de GitHub Actions[cite: 2, 9].

---

## Configuración Inicial

### 1. Base de Datos Local
Asegúrese de que el servicio de PostgreSQL esté activo y tenga creada la base de datos:

```sql
CREATE DATABASE citas_medicas_db;