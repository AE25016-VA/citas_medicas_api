package sv.ues.fmo.poo2.citas_medicas_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "especialidad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialidad")
    private Long idEspecialidad;

   @Column(name = "nombre_especialidad", nullable = false, unique = true, length = 100)
private String nombreEspecialidad;

    @Column(name = "descripcion_especialidad", columnDefinition = "TEXT")
    private String descripcionEspecialidad;

   
}