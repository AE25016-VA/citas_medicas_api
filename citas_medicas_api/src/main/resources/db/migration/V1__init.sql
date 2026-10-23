-- Active: 1788399080509@@localhost@5432@citas_medicas_db
-- ============================================================
-- FLYWAY MIGRATION V1: ESQUEMA INICIAL DE BASE DE DATOS
-- Proyecto: API Gestión de Citas Médicas
-- Motor: PostgreSQL
-- ============================================================

-- 1. Tabla Paciente
CREATE TABLE paciente (
    id_paciente BIGSERIAL PRIMARY KEY,
    nombre_paciente VARCHAR(100) NOT NULL,
    apellido_paciente VARCHAR(100) NOT NULL,
    correo_paciente VARCHAR(100) UNIQUE NOT NULL,
    contraseña_paciente VARCHAR(100) NOT NULL,
    telefono_paciente VARCHAR(100)
);

-- 2. Tabla Medico
CREATE TABLE medico (
    id_medico BIGSERIAL PRIMARY KEY,
    nombre_medico VARCHAR(50) NOT NULL,
    apellido_medico VARCHAR(50) NOT NULL,
    correo_medico VARCHAR(50) UNIQUE NOT NULL,
    nombre_usuario VARCHAR(50) UNIQUE NOT NULL,
    contraseña VARCHAR(50) NOT NULL,
    genero_medico VARCHAR(20) NOT NULL
);

-- 3. Tabla Especialidad
CREATE TABLE especialidad (
    id_especialidad BIGSERIAL PRIMARY KEY,
    nombre_especialidad VARCHAR(100) NOT NULL,
    descripcion_especialidad TEXT
);

-- 4. Tabla Intermedia Especialidad_Medico (Relación N:M)
CREATE TABLE especialidad_medico (
    medico_id BIGINT NOT NULL,
    especialidad_id BIGINT NOT NULL,
    PRIMARY KEY (medico_id, especialidad_id),
    CONSTRAINT fk_esp_med_medico FOREIGN KEY (medico_id) 
        REFERENCES medico(id_medico) ON DELETE CASCADE,
    CONSTRAINT fk_esp_med_especialidad FOREIGN KEY (especialidad_id) 
        REFERENCES especialidad(id_especialidad) ON DELETE CASCADE
);

-- 5. Tabla HorarioMedico
CREATE TABLE horario_medico (
    id_horario BIGSERIAL PRIMARY KEY,
    medico_id BIGINT NOT NULL,
    dias_turno VARCHAR(50) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    CONSTRAINT fk_horario_medico FOREIGN KEY (medico_id) 
        REFERENCES medico(id_medico) ON DELETE CASCADE
);

-- 6. Tabla Cita (Transaccional Central)
CREATE TABLE cita (
    id_cita BIGSERIAL PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    especialidad_id BIGINT NOT NULL,
    fecha_cita DATE NOT NULL,
    hora_cita TIME NOT NULL,
    estado_cita VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE',
    motivo_cita TEXT,
    fecha_asistencia TIMESTAMP,
    CONSTRAINT fk_cita_paciente FOREIGN KEY (paciente_id) 
        REFERENCES paciente(id_paciente),
    CONSTRAINT fk_cita_medico FOREIGN KEY (medico_id) 
        REFERENCES medico(id_medico),
    CONSTRAINT fk_cita_especialidad FOREIGN KEY (especialidad_id) 
        REFERENCES especialidad(id_especialidad)
);