# API de Gestión de Citas Médicas

Este repositorio contiene la documentación técnica y el diseño de la arquitectura de software para el desarrollo de una API REST de Gestión de Citas Médicas. El proyecto aborda la automatización, centralización y optimización del flujo transaccional de consultas médicas, coordinando horarios, especialidades y usuarios bajo una estructura de datos sólida y escalable.

---

## Integrantes del Equipo

| Nombre Completo | Carnet / Código |
| :--- | :---: |
| Cesia Mariena Alfaro Hernández | AH23007 |
| Vanesa Gabriela Arevalo Elias | AE25016 |
| Liliana Melissa Cruz Henriquez | CH20014 |
| Gerson Antonio Chámul Ramírez | CR25082 |
| Cristian Mauricio Molina Monzon | MM18192 |

---

## Modelado e Ingeniería de Datos

El proyecto documenta el ciclo completo de diseño de software mediante los siguientes artefactos:

1. **Diagrama de Casos de Uso (UML):** Delimita el alcance funcional del sistema y la interacción de los tres actores principales (Paciente, Médico y Administrador).
2. **Modelo Entidad-Relación (DER Conceptual):** Establece la estructura de información e identifica las reglas de negocio y cardinalidades del dominio (1:N y N:M).
3. **Diagrama Relacional (Base de Datos):** Muestra la traducción física del modelo conceptual a PostgreSQL, definiendo claves primarias (PK), claves foráneas (FK) y la resolución de relaciones complejas mediante tablas intermedias.
4. **Diagrama de Clases (UML):** Define la arquitectura orientada a objetos para el desarrollo backend, especificando atributos, visibilidad y métodos de negocio clave.

---

## Entidades Principales

* **`Paciente`:** Administra el perfil, contacto y credenciales de los usuarios solicitantes de citas.
* **`Medico`:** Gestiona el perfil del personal de salud encargado de las consultas.
* **`Especialidad` / `Especialidad_medico`:** Catálogo de áreas médicas y tabla asociativa para el soporte de múltiples especialidades por doctor.
* **`HorarioMedico`:** Controla los días, turnos y rango de disponibilidad de los doctores mediante métodos como `consultarDisponibilidad()`.
* **`Cita`:** Núcleo del sistema que vincula en una transacción única al paciente, médico y especialidad con su respectivo estado, fecha y hora.

---

## Tecnologías Planificadas

* **Lenguaje y Gestor de Proyectos:** Java con Apache Maven
* **Entorno de Desarrollo (IDE):** Visual Studio Code
* **Pruebas de API:** Postman
* **Motor de Base de Datos:** PostgreSQL
* **Estándar de Arquitectura:** API RESTful / Orientación a Objetos (UML)
* **Herramientas de Diseño:** Draw.io
