package sv.ues.fmo.poo2.citas_medicas_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "especialidad") // Nombre de la tabla en PostgreSQL
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincrementable 
    @Column(name = "id_especialidad")
    private Long idEspecialidad; // Long

    @Column(name = "nombre_especialidad", length = 100) // VARCHAR(100)
    private String nombreEspecialidad;

    @Column(name = "descripcion_especialidad", columnDefinition = "TEXT") // TEXT
    private String descripcionEspecialidad;

    // Constructor vacío Obligatorio para Spring
    public Especialidad() {
    }

    // Getters y Setters 
    public Long getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public String getDescripcionEspecialidad() {
        return descripcionEspecialidad;
    }

    public void setDescripcionEspecialidad(String descripcionEspecialidad) {
        this.descripcionEspecialidad = descripcionEspecialidad;
    }
}