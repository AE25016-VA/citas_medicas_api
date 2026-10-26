INSERT INTO especialidad (id_especialidad, nombre_especialidad, descripcion_especialidad) 
VALUES (1, 'Medicina General', 'Especialidad predeterminada')
ON CONFLICT (id_especialidad) DO NOTHING;

ALTER TABLE medico ADD COLUMN IF NOT EXISTS id_especialidad BIGINT;

UPDATE medico SET id_especialidad = 1 WHERE id_especialidad IS NULL;

ALTER TABLE medico ALTER COLUMN id_especialidad SET NOT NULL;

ALTER TABLE medico 
ADD CONSTRAINT fk_medico_especialidad 
FOREIGN KEY (id_especialidad) 
REFERENCES especialidad(id_especialidad);